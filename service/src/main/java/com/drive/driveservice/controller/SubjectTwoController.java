package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.SubjectOne;
import com.drive.driveservice.entity.SubjectTwo;
import com.drive.driveservice.service.SubjectOneService;
import com.drive.driveservice.service.SubjectTwoService;
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
@RequestMapping("/driveservice/subject-two")
@CrossOrigin
public class SubjectTwoController {

    @Autowired
    private SubjectTwoService subjectTwoService;

    @ApiOperation("修改科目所需学时")
    @PostMapping("updateTwoTime")
    public R getTwoTime(@RequestBody SubjectTwo subjectTwo){
        subjectTwoService.updateById(subjectTwo);
        return R.ok();
    }

    @ApiOperation("查看所有所需学时")
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){
        Page<SubjectTwo> subjectTwoPage = new Page<>(page,limit);
        subjectTwoService.page(subjectTwoPage,null);
        Long total = subjectTwoPage.getTotal();
        List<SubjectTwo> records = subjectTwoPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

}

