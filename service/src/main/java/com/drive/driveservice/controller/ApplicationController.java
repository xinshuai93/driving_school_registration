package com.drive.driveservice.controller;

import com.drive.commonutils.R;
import com.drive.commonutils.utils.FileDownload;
import com.drive.commonutils.utils.FileUpload;
import com.drive.driveservice.dto.ApplicationDTO;
import com.drive.driveservice.entity.Application;
import com.drive.driveservice.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 学生报名表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@RestController
@RequestMapping("/driveservice/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @ApiOperation("新增报名")
    @PostMapping("add")
    public R add(@RequestBody Application dto) {
        boolean save = applicationService.save(dto);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("文件上传")
    @PostMapping("Upload")
    public R uploadToLocal(MultipartFile file) {
        String s = FileUpload.uploadToLocal(file);
        System.out.println(s);
        return R.ok().data("path",s);
    }

    @ApiOperation("文件导出")
    @PostMapping("fileExport")
    public void fileExport(@RequestBody ApplicationDTO dto, HttpServletResponse response) {
        String file = dto.getFile();
        FileDownload.download(file,response);
    }

    @ApiOperation("单个查询")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id) {
//        applicationService.get(id);
        return R.ok();
    }

}

