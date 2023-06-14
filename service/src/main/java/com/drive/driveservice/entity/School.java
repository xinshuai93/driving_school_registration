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
@ApiModel(value="School对象", description="")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "驾校id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "驾校名称")
    private String schoolName;

    @ApiModelProperty(value = "驾校电话")
    private String schoolePhone;

    @ApiModelProperty(value = "驾校地点")
    private String schoolAdress;

    @ApiModelProperty(value = "驾校成立时间")
    private Date createTime;


}
