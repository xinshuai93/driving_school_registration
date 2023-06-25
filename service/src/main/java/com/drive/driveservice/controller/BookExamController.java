package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.*;
import com.drive.driveservice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lonerw
 * @since 2023-06-24
 */
@Api(tags = "预约考试")
@RestController
@RequestMapping("/driveservice/book-exam")
public class BookExamController {

    @Autowired
    private BookExamService bookExamService;

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

    @ApiOperation("能否预约考试")
    @GetMapping("isBook/{id}")
    public R isBook(@PathVariable String id){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        String type = studentService.getOne(wrapper).getLicenseType();
        String message = "true";
        Integer progress = studentService.getOne(wrapper).getLearnProgress();
        System.out.println(type);
        if (progress == 1){
            QueryWrapper<SubjectOne> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectOneService.getOne(wrapper1).getNeedTime()) {
                return R.ok().data("message",message);
            }else{
                message = "false";
                return R.ok().data("message",message);
            }
        }
        else if (progress == 2){
            QueryWrapper<SubjectTwo> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectTwoService.getOne(wrapper1).getNeedTime()) {
                return R.ok().data("message",message);
            }else{
                message = "false";
                return R.ok().data("message",message);
            }
        }
        else if (progress == 3){
            QueryWrapper<SubjectThree> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectThreeService.getOne(wrapper1).getNeedTime()) {
                return R.ok().data("message",message);
            }else{
                message = "false";
                return R.ok().data("message",message);
            }
        }
        else if (progress == 4){
            QueryWrapper<SubjectFour> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectFourService.getOne(wrapper1).getNeedTime()) {
                return R.ok().data("message",message);
            }else{
                message = "false";
                return R.ok().data("message",message);
            }
        }
        return R.error();
    }

    @ApiOperation("预约考试")
    @PostMapping("bookExam/{id}")
    public R bookExam(@PathVariable String id,@RequestBody BookExam bookExam){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        bookExam.setStudentId(id);
        wrapper.eq("id",id);
        bookExam.setName(studentService.getOne(wrapper).getName());
        bookExam.setSubjectType("科目"+studentService.getOne(wrapper).getLearnProgress().toString());
        bookExam.setType(studentService.getOne(wrapper).getLicenseType());
        bookExamService.save(bookExam);
        return R.ok();
    }

}

