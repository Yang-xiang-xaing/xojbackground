package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import indi.yuluo.xojbackgroundmanagmentsystem.common.BaseContext;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.service.UserService;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.JwtUtil;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-22  22:39
 * @Description: TODO
 */

@Slf4j
@RestController
@RequestMapping(value = "/user")
@Api(value = "后台管理系统用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录方法
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录方法", httpMethod = "POST")
    public Result<?> login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {

        String jwtToken = null;

        // 处理登录密码
        password = MD5Util.getMD5(password);

        UserInfo curUser = userService.getUserByUserNameAndCheckUserRole(username, password);

        if (Objects.nonNull(curUser)) {
            // 可以登录，生成jwt token凭证 并返回给前端
            // 错误交由globalExceptionHandler处理异常
            jwtToken = JwtUtil.createJWT(String.valueOf(curUser.getUuid()), 1000L * 60 * 60 * 24);
            // log.info(jwtToken);
        }

        return Result.success(jwtToken);

    }
}
