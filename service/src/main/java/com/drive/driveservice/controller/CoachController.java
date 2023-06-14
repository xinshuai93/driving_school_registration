package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.CoachDTO;
import com.drive.driveservice.entity.Coach;
import com.drive.driveservice.entity.Student;
import com.drive.driveservice.entity.vo.CoachQuery;
import com.drive.driveservice.service.CoachService;
import com.drive.driveservice.service.StudentService;
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
@RestController
@RequestMapping("/driveservice/coach")
@CrossOrigin
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private StudentService studentService;

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
    @PostMapping("getSelfStu/{page}/{limit}")
    public R getSelfStu(@PathVariable Long page,
                        @PathVariable Long limit,
                        @RequestBody(required = false) CoachDTO dto){
        Page<Student> studentPage = new Page<>(page,limit);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("coach_id",dto.getCoachId());
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
}

