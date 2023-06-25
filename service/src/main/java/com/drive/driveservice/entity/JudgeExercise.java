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
 * @since 2023-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="JudgeExercise对象", description="")
public class JudgeExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "判断题id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "题目")
    private String content;

    @ApiModelProperty(value = "选择")
    private String choose;

    @ApiModelProperty(value = "答案")
    private String key;

    @ApiModelProperty(value = "分数")
    private Integer score;


}
