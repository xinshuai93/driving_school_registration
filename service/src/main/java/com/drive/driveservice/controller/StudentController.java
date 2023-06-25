package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.entity.Student;
import com.drive.driveservice.entity.vo.StudentQuery;
import com.drive.driveservice.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Api(tags = "学员")
@RestController
@RequestMapping("/driveservice/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("新增学员")
    @PostMapping("addStudent")
    public R addStudent(@RequestBody Student student){
        studentService.save(student);
        return R.ok();
    }

    @ApiOperation("修改学员")
    @PostMapping("updateStudent")
    public R updateStudent(@RequestBody Student student){
        studentService.updateById(student);
        return R.ok();
    }

    @ApiOperation("删除学员")
    @DeleteMapping("deleteStudent/{id}")
    public R deleteStudent(@PathVariable String id){
        studentService.removeById(id);
        return R.ok();
    }

    @ApiOperation("分页查询")
    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) StudentQuery studentQuery){
        Page<Student> studentPage = new Page<>(page,limit);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        String name = studentQuery.getName();
        String phone = studentQuery.getPhone();
        Date begin = studentQuery.getBegin();
        Date end = studentQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(phone)){
            wrapper.like("phone",phone);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("registe_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("resigte_time",end);
        }
        studentService.page(studentPage,wrapper);
        Long total = studentPage.getTotal();
        List<Student> records = studentPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }


}

