package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.SubjectFour;
import com.drive.driveservice.entity.SubjectOne;
import com.drive.driveservice.service.SubjectFourService;
import com.drive.driveservice.service.SubjectOneService;
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
@RequestMapping("/driveservice/subject-four")
@CrossOrigin
public class SubjectFourController {

    @Autowired
    private SubjectFourService subjectFourService;

    @ApiOperation("修改科目所需学时")
    @PostMapping("updateFourTime")
    public R getFourTime(@RequestBody SubjectFour subjectFour){
        subjectFourService.updateById(subjectFour);
        return R.ok();
    }

    @ApiOperation("查看所有所需学时")
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){
        Page<SubjectFour> subjectFourPage = new Page<>(page,limit);
        subjectFourService.page(subjectFourPage,null);
        Long total = subjectFourPage.getTotal();
        List<SubjectFour> records = subjectFourPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

}

