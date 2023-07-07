package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.BookExam;
import com.drive.driveservice.entity.ExamRecord;
import com.drive.driveservice.entity.vo.ExamRecordVo;
import com.drive.driveservice.service.BookExamService;
import com.drive.driveservice.service.ExamRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lonerw
 * @since 2023-06-25
 */
@Api(tags = "考试记录")
@RestController
@RequestMapping("/driveservice/exam-record")
@CrossOrigin
public class ExamRecordController {

    @Autowired
    private ExamRecordService examRecordService;

    @Autowired
    private BookExamService bookExamService;

    @ApiOperation("获得学员考试记录")
    @PostMapping("getExamRecord/{id}")
    public R getExamRecord(@PathVariable String id){
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        List<ExamRecordVo> list = new ArrayList<>();
        wrapper.eq("student_id",id);
        Date date = new Date();
        List<BookExam> list1 = bookExamService.list(wrapper);
        for (int i = 0; i < list1.size(); i++) {
            ExamRecordVo examRecordVo = new ExamRecordVo();
            examRecordVo.setScore(0);
            examRecordVo.setSubject(list1.get(i).getSubjectType());
            examRecordVo.setTime(list1.get(i).getTime());
            if (list1.get(i).getIsPass() == 2) {
                examRecordVo.setState("预约中");
            }else if (list1.get(i).getIsPass() == 1){
                examRecordVo.setState("未通过");
            }else {
                Long diff = date.getTime()-list1.get(i).getTime().getTime();
                System.out.println(date);
                if(diff<=0){
                    examRecordVo.setState("已通过");
                }else {
                    continue;
                }
            }
            list.add(examRecordVo);
        }
        QueryWrapper<ExamRecord> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("stu_id",id);
        List<ExamRecord> list2 = examRecordService.list(wrapper1);
        for (int i = 0; i < list2.size(); i++) {
            ExamRecordVo examRecordVo = new ExamRecordVo();
            examRecordVo.setSubject(list2.get(i).getSubject());
            examRecordVo.setTime(list2.get(i).getTime());
            examRecordVo.setState("已完成");
            examRecordVo.setScore(list2.get(i).getScore());
            list.add(examRecordVo);
        }
        return R.ok().data("list",list);
    }

    @ApiOperation("插入成绩")
    @PostMapping("addGrade")
    public R addGrade(@RequestBody ExamRecord examRecord){
        examRecordService.save(examRecord);
        QueryWrapper<BookExam> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",examRecord.getStuId());
        wrapper.eq("is_pass",1);
        BookExam bookExam = bookExamService.getOne(wrapper);
        bookExam.setIsPass(4);
        bookExamService.updateById(bookExam);
        return R.ok();
    }
}

