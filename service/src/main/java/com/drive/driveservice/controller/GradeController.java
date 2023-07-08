package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.BookExam;
import com.drive.driveservice.entity.Grade;
import com.drive.driveservice.service.BookExamService;
import com.drive.driveservice.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lonerw
 * @since 2023-06-25
 */
@Api(tags = "成绩")
@RestController
@RequestMapping("/driveservice/grade")
@CrossOrigin
public class GradeController {

}

