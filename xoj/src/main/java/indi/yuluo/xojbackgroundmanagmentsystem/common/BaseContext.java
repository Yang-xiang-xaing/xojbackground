package indi.yuluo.xojbackgroundmanagmentsystem.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yuluo
 * @FileName: BaseContext.java
 * @createTime: 2022/5/23 16:26
 * @Description: 基于ThreadLocal封装的工具类 主要的作用是获取当前登录系统的用户id  以线程为作用域
 */

@Slf4j
public class BaseContext {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置用户名
     * @param username
     */
    public static void setCurrent(String username) {
        log.info("当前登录的用户: {}", username);
        threadLocal.set(username);
    }

    /**
     * 获取当前登录用户的id
     * @return
     */
    public static String getCurrent() {
        return threadLocal.get();
    }
}
