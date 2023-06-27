package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.Student;
import com.drive.driveservice.entity.SubjectOne;
import com.drive.driveservice.service.StudentService;
import com.drive.driveservice.service.SubjectOneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lonerw
 * @since 2023-06-24
 */
@Api(tags = "科目一")
@RestController
@RequestMapping("/driveservice/subject-one")
public class SubjectOneController {

    @Autowired
    private SubjectOneService subjectOneService;

    @ApiOperation("修改科目所需学时")
    @PostMapping("updateOneTime")
    public R getOneTime(@RequestBody SubjectOne subjectOne){
        subjectOneService.updateById(subjectOne);
        return R.ok();
    }

    @ApiOperation("查看所有所需学时")
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){
        Page<SubjectOne> subjectOnePage = new Page<>(page,limit);
        subjectOneService.page(subjectOnePage,null);
        Long total = subjectOnePage.getTotal();
        List<SubjectOne> records = subjectOnePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

}

