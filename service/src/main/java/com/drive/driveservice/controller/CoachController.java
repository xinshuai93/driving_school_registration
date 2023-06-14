package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.Coach;
import com.drive.driveservice.entity.vo.CoachQuery;
import com.drive.driveservice.service.CoachService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

}

