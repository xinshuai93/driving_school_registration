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
 * 教练表
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Coach对象", description="教练表")
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教练id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "教练资历")
    private String level;

    @ApiModelProperty(value = "教练姓名")
    private String name;

    @ApiModelProperty(value = "教练性别")
    private String sex;

    @ApiModelProperty(value = "教练电话")
    private String phone;


}
