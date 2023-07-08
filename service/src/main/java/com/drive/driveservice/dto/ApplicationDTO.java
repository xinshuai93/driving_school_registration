package com.drive.driveservice.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "学生报名DTO",description = "学生报名DTO")
public class ApplicationDTO {

    @ApiModelProperty("报名人姓名")
    private String name;

    @ApiModelProperty("报名人性别")
    private String sex;

    @ApiModelProperty("报名人年龄")
    private Integer age;

    @ApiModelProperty("报名人住址")
    private String adress;

    @ApiModelProperty("报名人身份证")
    private String personCard;

    @ApiModelProperty("驾照类型")
    private String type;

    @ApiModelProperty("上传附件的路径")
    private String file;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "报名失败通知")
    private String message;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;
}
