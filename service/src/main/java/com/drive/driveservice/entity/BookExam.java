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
 * @author lonerw
 * @since 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BookExam对象", description="")
public class BookExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预约id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "预约科目")
    private String subjectType;

    @ApiModelProperty(value = "预约时间")
    private Date time;

    @ApiModelProperty(value = "考试类型")
    private String type;

    @ApiModelProperty(value = "是否通过")
    private Integer isPass;


}
