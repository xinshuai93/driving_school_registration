package com.drive.driveservice.controller;


import com.drive.driveservice.entity.User;
import com.drive.driveservice.service.UserService;
import com.drive.commonutils.R;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-05-31
 */
@RestController
@RequestMapping("/driveservice/user")
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

}

