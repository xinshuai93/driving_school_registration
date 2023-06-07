package com.drive.driveservice.controller;


import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.drive.driveservice.dto.RecordReportDTO;
import com.drive.driveservice.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/driveservice/test")
public class TestController {
    @Autowired
    private TestService testService;

    @ApiOperation("记录-单个报告")
    @PostMapping("reportCheckRecord")
    public void reportCheckRecord(@RequestBody RecordReportDTO dto, HttpServletResponse response) {
       testService.reportCheckRecord(dto,response);
    }


}

