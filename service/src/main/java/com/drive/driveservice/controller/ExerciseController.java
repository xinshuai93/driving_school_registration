package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.CoachDTO;
import com.drive.driveservice.entity.Exercise;
import com.drive.driveservice.entity.Student;
import com.drive.driveservice.service.ExerciseService;
import com.drive.driveservice.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 预约练车表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-07-07
 */
@Api(tags = "预约练车")
@CrossOrigin
@RestController
@RequestMapping("/driveservice/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;


    @ApiOperation("预约练车")
    @PostMapping("add")
    public R add(@RequestBody Exercise exercise) {
        boolean save = exerciseService.save(exercise);
        if (save){
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("预约练车-分页查询")
    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) Exercise exercise) {
        Page<Exercise> page1 = new Page<>(page, limit);
        QueryWrapper<Exercise> wrapper = new QueryWrapper<>();
        wrapper.eq("coach_id",exercise.getCoachId());
        IPage<Exercise> page2 = exerciseService.page(page1, wrapper);
        List<Exercise> records = page2.getRecords();
        return R.ok().data("records",records);
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public R update(@RequestBody Exercise exercise) {
        boolean b = exerciseService.updateById(exercise);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id) {
        boolean b = exerciseService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("根据学生id获取预约练车信息")
    @GetMapping("getExercise/{id}")
    public R getExercise(@PathVariable String id) {
        QueryWrapper<Exercise> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",id);
        List<Exercise> list = exerciseService.list(wrapper);
        return R.ok().data("exerciseList",list);
    }




}

