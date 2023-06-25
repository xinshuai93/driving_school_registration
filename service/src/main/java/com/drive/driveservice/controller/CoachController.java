package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.CoachDTO;
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

    @ApiOperation("是否通过学员考试申请")
    @GetMapping("ispassApplication/{id}/{state}")
    public R isPassApplication(@PathVariable String id,@PathVariable Long state){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
        wrapper.eq("student_id",id);
        BookExam bookExam = bookExamService.getOne(wrapper);
        bookExam.setIsPass(state);
        wrapper1.eq("id",id);
        bookExam.setName(studentService.getOne(wrapper1).getName());
        bookExamService.updateById(bookExam);
        return R.ok();
    }

    @ApiOperation("查看学生考试成绩")
    @GetMapping("getStudentGrade/{id}/{page}/{limit}")
    public R getStudentGrade(@PathVariable String id,
                             @PathVariable Long page,
                             @PathVariable Long limit){
        QueryWrapper<Grade> wrapper = new QueryWrapper<>();
        wrapper.eq("coach_id",id);
        Page<Grade> gradePage = new Page<>(page,limit);
        gradeService.page(gradePage,wrapper);
        Long total = gradePage.getTotal();
        List<Grade> records = gradePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

}

