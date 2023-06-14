package com.drive.driveservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学员id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学员姓名")
    private String name;

    @ApiModelProperty(value = "学员年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private Boolean sex;

    @ApiModelProperty(value = "身份证")
    private String card;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "学员住址")
    private String adress;

    @ApiModelProperty(value = "驾照类型")
    private String licenseType;

    @ApiModelProperty(value = "报名时间")
    private Date registeTime;

    @ApiModelProperty(value = "紧急联系人电话")
    private String emergencyContactPhone;

    @ApiModelProperty(value = "健康状况")
    private String healthCondition;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户id")
    private String userId;


}
