package com.drive.driveservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "分配账号DTO",description = "分配账号DTO")
public class AssignAccountDTO {
    private String phone;
    private String password;
    private Integer role;
}
