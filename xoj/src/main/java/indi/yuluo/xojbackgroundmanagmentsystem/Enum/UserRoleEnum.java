package indi.yuluo.xojbackgroundmanagmentsystem.Enum;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-23  10:42
 * @Description: TODO
 */

public enum UserRoleEnum {

    ROOT(1000L, "超级管理员"),
    ADMIN(1001L, "管理员"),
    DEFAULT_USER(1003L, "默认用户");


    UserRoleEnum(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    private Long code;
    private String description;

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
