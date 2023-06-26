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
@ApiModel(value="SubjectThree对象", description="")
public class SubjectThree implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科目三id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学习资料id")
    private String chapterId;

    @ApiModelProperty(value = "所需学习时长")
    private Double needTime;

    @ApiModelProperty(value = "驾照类型")
    private String type;


}
