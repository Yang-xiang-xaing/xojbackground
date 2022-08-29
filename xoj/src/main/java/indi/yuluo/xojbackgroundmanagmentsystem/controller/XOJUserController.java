package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserRole;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.exception.RegisterException;
import indi.yuluo.xojbackgroundmanagmentsystem.service.UserRoleService;
import indi.yuluo.xojbackgroundmanagmentsystem.service.UserService;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-27  09:53
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/xoj/user")
@Api(value = "XOJ用户控制器")
public class XOJUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取XOJ后台数据库所有的用户信息
     *
     * @param page     页码
     * @param pageSize 每页展示的数据数
     * @return 分页对象
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取XOJ后台数据库所有的用户信息", notes = "分页查询")
    public Result<?> getAllXOJUser(int page, int pageSize) {

        Page<UserInfo> user = userService.getAll(page, pageSize);

        return Result.success(user);
    }

    /**
     * 根据用户名模糊查询信息并返回
     *
     * @param page     页码
     * @param pageSize 每页展示的数据数
     * @param username 用户名，模糊查询的条件
     * @return 分页对象
     */
    @GetMapping("/getByUsername")
    @ApiOperation(value = "根据用户名模糊查询信息并返回", notes = "分页查询")
    public Result<?> getByUsername(int page, int pageSize, String username) {

        Page<UserInfo> res = userService.getByUsername(page, pageSize, username);

        return Result.success(res);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息对象
     * @return
     */
    @PostMapping("/updateUserById")
    public Result<?> updateUserInfo(@RequestBody UserInfo userInfo) {

        // 新增用户时，根据用户名去数据库查询，如果用户名重复，抛出用户注册异常
        UserInfo userInfo1 = userService.checkUsername(userInfo);
        if (Objects.nonNull(userInfo1)) {
            throw new RegisterException("用户名已存在！");
        }

        userService.updateById(userInfo);

        return Result.success("用户信息修改成功！");
    }

    /**
     * 删除用户
     *
     * @param userInfo 用户信息对象
     * @return
     */
    @PostMapping("/removeUserById")
    public Result<?> removeUser(@RequestBody UserInfo userInfo) {

        userService.removeById(userInfo);

        // 删除时同步删除用户权限中的数据
        userRoleService.removeById(userInfo.getUuid());

        return Result.success("删除用户成功");
    }

    /**
     * 新增用户
     *
     * @param userInfo 用户信息对象
     * @return
     */
    @PostMapping("/insertUser")
    public Result<?> insertUser(@RequestBody UserInfo userInfo) {

        // 新增用户时，根据用户名去数据库查询，如果用户名重复，抛出用户注册异常
        UserInfo userInfo1 = userService.checkUsername(userInfo);
        if (Objects.nonNull(userInfo1)) {
            throw new RegisterException("用户名已存在！");
        }

        // 使用md5加密密码
        String password = MD5Util.getMD5(userInfo.getPassword());
        userInfo.setPassword(password);

        // 保存
        userService.save(userInfo);
        // 设置用户默认权限
        UserRole userRole = new UserRole();
        userRole.setUid(userInfo.getUuid());
        userRoleService.save(userRole);

        return Result.success("新增用户成功！");
    }

}
