package indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yuluo
 * @CreateTime: 2022-07-19  18:00
 * @Description: TODO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value="UserInfo对象", description="")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid",type = IdType.UUID)
    private String uuid;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotNull
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{5,16}$)|(^[\\u2E80-\\u9FFF]{2,5})",
            message = "用户名必须是5-16位英文和数字的组合或者2-5位中文")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码")
    @NotNull
    @Pattern(regexp = "(^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).{8,16}$)",
            message = "由字母、数字、特殊字符，任意2种组成，8-16位")
    private String password;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "性别")
    private String gender;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "github地址")
    private String github;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "博客地址")
    private String blog;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "个性介绍")
    private String signature;

    @ApiModelProperty(value = "0可用，-1不可用")
    private int status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
