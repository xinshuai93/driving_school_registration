package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.CoachDTO;
import com.drive.driveservice.dto.LearnTimeDTO;
import com.drive.driveservice.entity.BookExam;
import com.drive.driveservice.entity.Coach;
import com.drive.driveservice.entity.Grade;
import com.drive.driveservice.entity.Student;
import com.drive.driveservice.entity.vo.CoachQuery;
import com.drive.driveservice.service.BookExamService;
import com.drive.driveservice.service.CoachService;
import com.drive.driveservice.service.GradeService;
import com.drive.driveservice.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 教练表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Api(tags = "教练")
@RestController
@RequestMapping("/driveservice/coach")
@CrossOrigin
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookExamService bookExamService;

    @Autowired
    private GradeService gradeService;


    @ApiOperation("新增教练")
    @PostMapping("addCoach")
    public R addCoach(@RequestBody Coach coach){
        coachService.save(coach);
        return R.ok();
    }

    @ApiOperation("修改教练")
    @PostMapping("updateCoach")
    public R updateCoach(@RequestBody Coach coach){
        coachService.updateById(coach);
        return R.ok();
    }

    @ApiOperation("删除教练")
    @DeleteMapping("deleteCoach/{id}")
    public R deleteCoach(@PathVariable String id){
        coachService.removeById(id);
        return R.ok();
    }

    @ApiOperation("分页查询")
    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) CoachQuery coachQuery){
        Page<Coach> coachPage = new Page<>(page,limit);
        QueryWrapper<Coach> wrapper = new QueryWrapper<>();
        String name = coachQuery.getName();
        String level = coachQuery.getLevel();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        coachService.page(coachPage,wrapper);
        Long total = coachPage.getTotal();
        List<Coach> records = coachPage.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation("查询自己学员")
    @PostMapping("getSelfStu/{id}/{page}/{limit}")
    public R getSelfStu(@PathVariable String id,
                        @PathVariable Long page,
                        @PathVariable Long limit,
                        @RequestBody(required = false) CoachDTO dto){
        Page<Student> studentPage = new Page<>(page,limit);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("coach_id",id);
        String name = dto.getName();
        String phone = dto.getPhone();
        Date begin = dto.getBegin();
        Date end = dto.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(phone)){
            wrapper.like("phone",phone);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("registe_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("resigte_time",end);
        }
        studentService.page(studentPage,wrapper);
        Long total = studentPage.getTotal();
        List<Student> records = studentPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation("获取审核人员")
    @GetMapping("getCheckStu/{id}")
    public R getCheckStu(@PathVariable String id){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("coach_id",id);
        QueryWrapper<BookExam> wrapper1 = new QueryWrapper<>();
        List<Student> list = studentService.list(wrapper);
        wrapper1.eq("is_pass",2);
        List<BookExam> list1 = bookExamService.list(wrapper1);
        List<BookExam> list2 =new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list1.size(); j++) {
                if (list.get(i).getId().equals(list1.get(j).getStudentId())){
                    list2.add(list1.get(j));
                    break;
                }
            }
        }
        return R.ok().data("list",list2);
    }

    @ApiOperation("通过审核")
    @GetMapping("passCheck/{id}")
    public R passCheck(@PathVariable String id){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        BookExam bookExam = bookExamService.getOne(wrapper);
        bookExam.setIsPass(1);
        if (bookExam.getSubjectType().equals("科目1")) {
            bookExam.setPaperId("1");
        }else {
            bookExam.setPaperId("2");
        }
        bookExamService.updateById(bookExam);
        return R.ok();
    }

    @ApiOperation("驳回审核")
    @GetMapping("rejectCheck/{id}")
    public R rejectCheck(@PathVariable String id){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        BookExam bookExam = bookExamService.getOne(wrapper);
        bookExam.setIsPass(3);
        bookExamService.updateById(bookExam);
        return R.ok();
    }

    @ApiOperation("教练给学生打学时")
    @PostMapping("addLearnTime")
    public R addLearnTime(@RequestBody LearnTimeDTO learnTimeDTO) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id", learnTimeDTO.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long diff;
        try {
            Student student = studentService.getOne(wrapper);
            Date begin = format.parse(learnTimeDTO.getBegin());
            Date end = format.parse(learnTimeDTO.getEnd());
            diff = (end.getTime() - begin.getTime())/(60 * 1000);
            double dif = (double) diff;
            student.setLearnTime(dif+studentService.getOne(wrapper).getLearnTime());
            studentService.updateById(student);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }

    @ApiOperation("教练查看自己学员成绩")
    @PostMapping("getSelfStuGrade/{id}")
    public R getSelfStuGrade(@PathVariable String id){
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("coach_id",id);
        List<Grade> gradeList = new ArrayList<>();
        List<Student> studentList =  studentService.list(studentQueryWrapper);
        for (int i = 0; i < studentList.size(); i++) {
            QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
            gradeQueryWrapper.eq("stu_id",studentList.get(i).getId());
            Grade grade = gradeService.getOne(gradeQueryWrapper);
            grade.setStuName(studentList.get(i).getName());
            gradeList.add(grade);
        }

        return R.ok().data("data",gradeList);
    }

}

