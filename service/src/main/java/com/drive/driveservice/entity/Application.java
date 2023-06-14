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
 * 学生报名表
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Application对象", description="学生报名表")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报名表id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "报名人姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "地址")
    private String adress;

    @ApiModelProperty(value = "身份证")
    private String personCard;

    @ApiModelProperty(value = "驾照类型")
    private String type;

    @ApiModelProperty(value = "体检表路径")
    private String file;

    @ApiModelProperty(value = "身份证照片")
    private String pictureCard;


}
