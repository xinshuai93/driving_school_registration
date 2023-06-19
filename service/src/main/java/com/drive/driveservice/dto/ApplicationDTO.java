package com.drive.driveservice.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "学生报名DTO",description = "学生报名DTO")
public class ApplicationDTO {

    @ApiModelProperty("报名人姓名")
    private String name;

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

    @ApiModelProperty("身份证照片")
    private String pictureCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;


    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
