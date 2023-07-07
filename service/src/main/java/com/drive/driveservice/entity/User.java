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
 * @author lx
 * @since 2023-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "姓名(必须实名)")
    private String name;

    @ApiModelProperty(value = "性别:1为男，2为女")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户角色:1:系统管理员, 2：教练, 3:学员")
    private Integer role;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "具体用户的id")
    private String roleId;


}
