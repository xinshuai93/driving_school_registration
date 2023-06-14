package com.drive.driveservice.controller;


import com.drive.commonutils.R;
import com.drive.driveservice.entity.School;
import com.drive.driveservice.service.SchoolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@RestController
@RequestMapping("/driveservice/school")
@CrossOrigin
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @ApiOperation("查找驾校")
    @GetMapping("getSchool/{id}")
    public R getSchool(@PathVariable String id){
        schoolService.getById(id);
        return R.ok();
    }

    @ApiOperation("修改驾校")
    @PostMapping("updateSchool")
    public R updateSchool(@RequestBody School school){
        schoolService.updateById(school);
        return R.ok();
    }

}

