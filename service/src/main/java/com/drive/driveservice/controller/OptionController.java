package com.drive.driveservice.controller;


import com.drive.commonutils.R;
import com.drive.driveservice.entity.Options;
import com.drive.driveservice.service.OptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 选项表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-27
 */
@Api(tags = "选项管理")
@RestController
@RequestMapping("/driveservice/option")
@CrossOrigin
public class OptionController {

    @Autowired
    private OptionService optionService;


    @ApiOperation("添加选项")
    @PostMapping("addOption")
    public R addOption(@RequestBody Options options) {
        boolean save = optionService.save(options);
        if (save) {
            return R.ok();
        }
        return R.error();
    }







}

