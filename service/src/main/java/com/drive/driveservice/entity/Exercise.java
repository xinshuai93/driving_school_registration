package com.drive.driveservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预约练车表
 * </p>
 *
 * @author lx
 * @since 2023-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Exercise对象", description="预约练车表")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预约练车id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "教练id")
    private String coachId;

    @ApiModelProperty(value = "预约练车科目，是科目二还是科目三")
    private String type;

    @ApiModelProperty(value = "预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    @ApiModelProperty(value = "是否通过")
    @TableField("isPass")
    private String isPass;


}
