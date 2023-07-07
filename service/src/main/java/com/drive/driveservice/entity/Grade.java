package com.drive.driveservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author lonerw
 * @since 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Grade对象", description="")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "成绩单id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生id")
    private String stuId;

    @ApiModelProperty(value = "科一成绩")
    private Integer subjectOne;

    @ApiModelProperty(value = "科二成绩")
    private Integer subjectTwo;

    @ApiModelProperty(value = "科三成绩")
    private Integer subjectThree;

    @ApiModelProperty(value = "科四成绩")
    private Integer subjectFour;

    @ApiModelProperty(value = "学生姓名")
    private String stuName;



}
