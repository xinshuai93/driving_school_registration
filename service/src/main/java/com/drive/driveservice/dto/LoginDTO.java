package com.drive.driveservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "登录DTO",description = "登录DTO")
public class LoginDTO {
    private String phone;
    private String password;
    private Integer role;
}
