package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.SubjectOne;
import com.drive.driveservice.entity.SubjectThree;
import com.drive.driveservice.service.SubjectOneService;
import com.drive.driveservice.service.SubjectThreeService;
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
@RestController
@RequestMapping("/driveservice/subject-three")
@CrossOrigin
public class SubjectThreeController {

    @Autowired
    private SubjectThreeService subjectThreeService;

    @ApiOperation("修改科目所需学时")
    @PostMapping("updateThreeTime")
    public R getThreeTime(@RequestBody SubjectThree subjectThree){
        subjectThreeService.updateById(subjectThree);
        return R.ok();
    }

    @ApiOperation("查看所有所需学时")
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){
        Page<SubjectThree> subjectThreePage = new Page<>(page,limit);
        subjectThreeService.page(subjectThreePage,null);
        Long total = subjectThreePage.getTotal();
        List<SubjectThree> records = subjectThreePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

}

