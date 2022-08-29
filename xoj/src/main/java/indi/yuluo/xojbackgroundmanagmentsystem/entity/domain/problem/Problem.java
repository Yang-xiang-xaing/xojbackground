package indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem;

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
 * @CreateTime: 2022-08-07  22:04
 * @Description: TODO
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Problem对象", description="")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题目的自定义ID 例如（HOJ-1000）")
    private String problemId;

    @ApiModelProperty(value = "题目")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "0为ACM,1为OI")
    private Integer type;

    @ApiModelProperty(value = "单位ms")
    private Integer timeLimit;

    @ApiModelProperty(value = "单位mb")
    private Integer memoryLimit;

    @ApiModelProperty(value = "单位mb")
    private Integer stackLimit;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "题面样例")
    private String examples;

    @ApiModelProperty(value = "题目来源")
    private String source;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

    @ApiModelProperty(value = "备注,提醒")
    private String hint;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
