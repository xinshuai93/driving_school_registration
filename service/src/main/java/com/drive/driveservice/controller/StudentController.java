package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.*;
import com.drive.driveservice.entity.vo.ExamInfoVo;
import com.drive.driveservice.entity.vo.StudentQuery;
import com.drive.driveservice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Api(tags = "学员")
@RestController
@RequestMapping("/driveservice/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectOneService subjectOneService;

    @Autowired
    private SubjectTwoService subjectTwoService;

    @Autowired
    private SubjectThreeService subjectThreeService;

    @Autowired
    private SubjectFourService subjectFourService;

    @Autowired
    private BookExamService bookExamService;

    @Autowired
    private ExamService examService;

    @Autowired
    private GradeService gradeService;

    @ApiOperation("新增学员")
    @PostMapping("addStudent")
    public R addStudent(@RequestBody Student student){
        studentService.save(student);
        return R.ok();
    }

    @ApiOperation("修改学员")
    @PostMapping("updateStudent")
    public R updateStudent(@RequestBody Student student){
        studentService.updateById(student);
        return R.ok();
    }

    @ApiOperation("删除学员")
    @DeleteMapping("deleteStudent/{id}")
    public R deleteStudent(@PathVariable String id){
        studentService.removeById(id);
        return R.ok();
    }

    @ApiOperation("通过id查询学员")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id) {
        Student byId = studentService.getById(id);
        return R.ok().data("student",byId);
    }

    @ApiOperation("分页查询")
    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) StudentQuery studentQuery){
        Page<Student> studentPage = new Page<>(page,limit);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        String name = studentQuery.getName();
        String phone = studentQuery.getPhone();
        Date begin = studentQuery.getBegin();
        Date end = studentQuery.getEnd();
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

    @ApiOperation("查看自己所需学时")
    @GetMapping("getNeedTime/{id}")
    public R getNeedTime(@PathVariable String id){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        String type = studentService.getOne(wrapper).getLicenseType();
        Integer progress = studentService.getOne(wrapper).getLearnProgress();
        Double needTime = 0.0;
        if (progress == 1) {
            QueryWrapper<SubjectOne> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            needTime = subjectOneService.getOne(wrapper1).getNeedTime()-studentService.getOne(wrapper).getLearnTime();
            return R.ok().data("needTime",needTime);
        }
        else if (progress == 2) {
            QueryWrapper<SubjectTwo> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            needTime = subjectTwoService.getOne(wrapper1).getNeedTime()-studentService.getOne(wrapper).getLearnTime();
            return R.ok().data("needTime",needTime);
        }
        else if (progress == 3) {
            QueryWrapper<SubjectThree> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            needTime = subjectThreeService.getOne(wrapper1).getNeedTime()-studentService.getOne(wrapper).getLearnTime();
            return R.ok().data("needTime",needTime);
        }
        else if (progress == 4) {
            QueryWrapper<SubjectFour> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            needTime = subjectFourService.getOne(wrapper1).getNeedTime()-studentService.getOne(wrapper).getLearnTime();
            return R.ok().data("needTime",needTime);
        }
        return R.error();
    }

    @ApiOperation("获取自己的考试信息")
    @GetMapping("getExamInfo/{id}")
    public R getExamInfo(@PathVariable String id){
        ExamInfoVo examInfoVo = new ExamInfoVo();
        QueryWrapper<BookExam> wrapper =new QueryWrapper<>();
        wrapper.eq("student_id",id);
        wrapper.eq("is_pass",1);
        BookExam bookExam = bookExamService.getOne(wrapper);
        if (bookExam == null) {
            String data = "未查到考试记录";
            return R.ok().data("data",data);
        }
        examInfoVo.setTime(bookExam.getTime());
        examInfoVo.setSubjectType(bookExam.getSubjectType());
        QueryWrapper<Exam> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id",bookExam.getPaperId());
        Exam exam = examService.getOne(wrapper1);
        examInfoVo.setChooseNum(exam.getChooseNum());
        examInfoVo.setDuration(exam.getTime());
        examInfoVo.setJudgeNum(exam.getJudgeNum());
        examInfoVo.setMultipleNum(exam.getMultipleNum());
        return R.ok().data("data",examInfoVo);
    }

    @ApiOperation("学员查询自己成绩")
    @GetMapping("getStuGrade/{id}")
    public R getStuGrade(@PathVariable String id){
        return R.ok().data("data",gradeService.getById(id));
    }

    @ApiOperation("查询自己的预约考试记录")
    @GetMapping("getStuBookExam")
    public R getStuBookExam(@PathVariable String id){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",id);
        List<BookExam> list = bookExamService.list(wrapper);
        return R.ok().data("list",list);
    }


}

