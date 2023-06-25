package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.driveservice.dto.AssignAccountDTO;
import com.drive.driveservice.entity.User;
import com.drive.driveservice.entity.vo.UserQuery;
import com.drive.driveservice.service.UserService;
import com.drive.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-05-31
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/driveservice/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("新增用户")
    @PostMapping("addUser")
    public R addUser(@RequestBody User user) {
        userService.save(user);
        return R.ok();
    }

    @ApiOperation("根据id查找用户")
    @GetMapping("selectUser/{id}")
    public R selectUser(@PathVariable String id) {
        User byId = userService.getById(id);
        return R.ok().data("user",byId);
    }

    @ApiOperation("修改用户")
    @PostMapping("updateUser")
    public R updateUser(@RequestBody User user){
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("deleteUser/{id}")
    public R deleteUser(@PathVariable String id){
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation("分页查询")
    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) UserQuery userQuery){
        Page<User> userPage = new Page<>(page,limit);
        String name = userQuery.getName();
        String sex = userQuery.getSex();
        String phone = userQuery.getPhone();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(sex)){
            wrapper.eq("sex",sex);
        }
        if (!StringUtils.isEmpty(phone)){
            wrapper.eq("phone",phone);
        }
        userService.page(userPage,wrapper);
        Long total = userPage.getTotal();
        List<User> records = userPage.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation("分配账号")
    @PostMapping("AssignAccount")
    public R AssignAccount(@RequestBody AssignAccountDTO dto){
        User user = new User();
        //前端在报名通过后需要设置用户类型
        user.setRole(dto.getRole());
        user.setPhone(dto.getPhone());
        user.setPassword("123456");
        boolean isSave = userService.save(user);
        if (isSave) {
            return R.ok();
        }
        return R.error();
    }
}

