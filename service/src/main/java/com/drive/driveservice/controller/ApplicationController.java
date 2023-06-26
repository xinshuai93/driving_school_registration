package com.drive.driveservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.commonutils.utils.FileDownload;
import com.drive.commonutils.utils.FileUpload;
import com.drive.driveservice.dto.ApplicationDTO;
import com.drive.driveservice.entity.Application;
import com.drive.driveservice.entity.vo.ApplicationQuery;
import com.drive.driveservice.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import org.joda.time.LocalDateTime;
import org.apache.catalina.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Application byId = applicationService.getById(id);
        String file = byId.getFile();
        String substring = file.substring(file.lastIndexOf("\\") + 1);
        byId.setFile(substring);
        return R.ok().data("application",byId);
    }

    @ApiOperation("审核通过后发送邮件告知学生报名成功并且发送账号和初始密码")
    @PostMapping("/sendMail")
    public R sendMail(@RequestBody ApplicationDTO dto) throws MessagingException, GeneralSecurityException {
        String s = applicationService.sendMail(dto);
        return R.ok().data("result",s);
    }

    @ApiOperation("分页查询")
    @PostMapping("pageApplication")
    public R pageApplication(@PathVariable Long page,
                             @PathVariable Long limit,
                             @RequestBody(required = false) ApplicationQuery applicationQuery){
        Page<Application> applicationPage = new Page<>(page,limit);
        QueryWrapper<Application> wrapper = new QueryWrapper<>();
        Date begin = applicationQuery.getBegin();
        Date end = applicationQuery.getEnd();
        Boolean isPass = applicationQuery.getIsPass();
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("create_time",end);
        }
        if (!StringUtils.isEmpty(isPass)){
            wrapper.eq("is_pass",isPass);
        }
        applicationService.page(applicationPage,wrapper);
        Long total = applicationPage.getTotal();
        List<Application> records = applicationPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }


}

