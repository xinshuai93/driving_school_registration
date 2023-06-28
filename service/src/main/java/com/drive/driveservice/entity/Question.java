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
 * 题目-答案表
 * </p>
 *
 * @author lx
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Question对象", description="题目-答案表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题目id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "答案id")
    private String answerId;

    @ApiModelProperty(value = "题目类型,1:单选,2:多选,3:判断")
    private Integer type;

    @ApiModelProperty(value = "所属科目,1:科目1,2:科目4")
    private Integer kemu;

    @ApiModelProperty(value = "解析")
    private String explains;


}
