package com.drive.driveservice.controller;


import com.drive.driveservice.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@RestController
@RequestMapping("/driveservice/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

}

