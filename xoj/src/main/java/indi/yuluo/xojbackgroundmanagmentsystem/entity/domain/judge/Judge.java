package indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.judge;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-07  21:57
 * @Description: TODO
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Judge对象", description="")
public class Judge implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "submit_id", type = IdType.AUTO)
    private Long submitId;

    @ApiModelProperty(value = "题目id")
    private Long pid;

    @ApiModelProperty(value = "题目展示id")
    private String displayPid;

    @ApiModelProperty(value = "用户id")
    private String uid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "提交的时间")
    private Date submitTime;

    @ApiModelProperty(value = "结果码具体参考文档")
    private Integer status;

    @ApiModelProperty(value = "0为仅自己可见，1为全部人可见。")
    private Boolean share;

    @ApiModelProperty(value = "错误提醒（编译错误，或者vj提醒）")
    private String errorMessage;

    @ApiModelProperty(value = "运行时间(ms)")
    private Integer time;

    @ApiModelProperty(value = "运行内存(kb)")
    private Integer memory;

    @ApiModelProperty(value = "IO判题不为空")
    private Integer score;

    @ApiModelProperty(value = "代码长度")
    private Integer length;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "代码语言")
    private String language;

    @ApiModelProperty(value = "判题机名称")
    private String judger;

    @ApiModelProperty(value = "提交者所在ip")
    private String ip;

    @ApiModelProperty(value = "废弃")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
