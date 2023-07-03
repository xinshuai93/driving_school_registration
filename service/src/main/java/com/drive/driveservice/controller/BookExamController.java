package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.*;
import com.drive.driveservice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@CrossOrigin
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

//    @ApiOperation("能否预约考试")
//    @GetMapping("isBook/{id}")
//    public R isBook(@PathVariable String id){
//        QueryWrapper<Student> wrapper = new QueryWrapper<>();
//        wrapper.eq("id",id);
//        String type = studentService.getOne(wrapper).getLicenseType();
//        String message = "true";
//        Integer progress = studentService.getOne(wrapper).getLearnProgress();
//        if (progress == 1){
//            QueryWrapper<SubjectOne> wrapper1 = new QueryWrapper<>();
//            wrapper1.eq("type",type);
//            if (studentService.getOne(wrapper).getLearnTime()>=subjectOneService.getOne(wrapper1).getNeedTime()) {
//                return R.ok().data("message",message);
//            }else{
//                message = "false";
//                return R.ok().data("message",message);
//            }
//        }
//        else if (progress == 2){
//            QueryWrapper<SubjectTwo> wrapper1 = new QueryWrapper<>();
//            wrapper1.eq("type",type);
//            if (studentService.getOne(wrapper).getLearnTime()>=subjectTwoService.getOne(wrapper1).getNeedTime()) {
//                return R.ok().data("message",message);
//            }else{
//                message = "false";
//                return R.ok().data("message",message);
//            }
//        }
//        else if (progress == 3){
//            QueryWrapper<SubjectThree> wrapper1 = new QueryWrapper<>();
//            wrapper1.eq("type",type);
//            if (studentService.getOne(wrapper).getLearnTime()>=subjectThreeService.getOne(wrapper1).getNeedTime()) {
//                return R.ok().data("message",message);
//            }else{
//                message = "false";
//                return R.ok().data("message",message);
//            }
//        }
//        else if (progress == 4){
//            QueryWrapper<SubjectFour> wrapper1 = new QueryWrapper<>();
//            wrapper1.eq("type",type);
//            if (studentService.getOne(wrapper).getLearnTime()>=subjectFourService.getOne(wrapper1).getNeedTime()) {
//                return R.ok().data("message",message);
//            }else{
//                message = "false";
//                return R.ok().data("message",message);
//            }
//        }
//        return R.error();
//    }

    @ApiOperation("预约考试")
    @PostMapping("bookExam/{id}")
    public R bookExam(@PathVariable String id,@RequestBody BookExam bookExam){
        String pp = "不能重复预约考试";
        if (cantRepeat(id).equals(false)) {
            return R.error().data("data",pp);
        }
        bookExam.setIsPass(2);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        bookExam.setStudentId(id);
        wrapper.eq("id",id);
        bookExam.setName(studentService.getOne(wrapper).getName());
        bookExam.setSubjectType("科目"+studentService.getOne(wrapper).getLearnProgress().toString());
        bookExam.setType(studentService.getOne(wrapper).getLicenseType());
        String type = studentService.getOne(wrapper).getLicenseType();
        String message = "true";
        Integer progress = studentService.getOne(wrapper).getLearnProgress();
        System.out.println(progress);
        if (progress == 1){
            QueryWrapper<SubjectOne> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectOneService.getOne(wrapper1).getNeedTime()) {
                bookExamService.save(bookExam);
                return R.ok().data("message",message);
            }else{
                message = "学时不够无法预约";
                return R.error().data("message",message);
            }
        }
        else if (progress == 2){
            QueryWrapper<SubjectTwo> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectTwoService.getOne(wrapper1).getNeedTime()) {
                bookExamService.save(bookExam);
                return R.ok().data("message",message);
            }else{
                message = "学时不够无法预约";
                return R.error().data("message",message);
            }
        }
        else if (progress == 3){
            QueryWrapper<SubjectThree> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectThreeService.getOne(wrapper1).getNeedTime()) {
                bookExamService.save(bookExam);
                return R.ok().data("message",message);
            }else{
                message = "学时不够无法预约";
                return R.error().data("message",message);
            }
        }
        else if (progress == 4){
            QueryWrapper<SubjectFour> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("type",type);
            if (studentService.getOne(wrapper).getLearnTime()>=subjectFourService.getOne(wrapper1).getNeedTime()) {
                bookExamService.save(bookExam);
                return R.ok().data("message",message);
            }else{
                message = "学时不够无法预约";
                return R.error().data("message",message);
            }
        }
        return R.error();
    }

    @ApiOperation("去除重复预约")
    @GetMapping("cantRepeat/{id}")
    public Boolean cantRepeat(@PathVariable String id){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",id);
        List<BookExam> list = bookExamService.list(wrapper);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsPass() == 2) {
                return false;
            }
        }
        return true;
    }
}

