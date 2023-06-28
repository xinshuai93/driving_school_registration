package com.drive.driveservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.LoginDTO;
import com.drive.driveservice.entity.User;
import com.drive.driveservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@RestController
@RequestMapping("/driveservice/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录验证")
    @PostMapping("/login")
    public R login(@RequestBody LoginDTO dto){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",dto.getPhone());
        User user = userService.list(wrapper).get(0);
        String roleId = user.getRoleId();
        String fail = "fail";
        if (user.getPassword().equals(dto.getPassword())) {
            return R.ok().data("message",roleId);
        }
        return R.error().data("message",fail);
    }

}
