package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.Enum.UserRoleEnum;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.Role;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserRole;
import indi.yuluo.xojbackgroundmanagmentsystem.exception.LoginException;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.UserMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.RoleService;
import indi.yuluo.xojbackgroundmanagmentsystem.service.UserRoleService;
import indi.yuluo.xojbackgroundmanagmentsystem.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-22  22:44
 * @Description: TODO
 */

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 根据用户名查询用户并且判断用户权限 只有超级管理员和管理员可以登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录凭证
     */
    @Override
    public UserInfo getUserByUserNameAndCheckUserRole(String username, String password) throws LoginException {

        // 先查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUsername, username);
        UserInfo userInfo = userMapper.selectOne(lambdaQueryWrapper);

        // 验证用户是否存在
        if (Objects.isNull(userInfo)) {
            throw new LoginException("用户不存在");
        }
        // 验证密码
        String userInfoPassword = userInfo.getPassword();
        if (!Objects.equals(userInfoPassword, password)) {
            throw new LoginException("用户密码错误");
        }

        // 查询用户权限表，获得用户权限id
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUid, userInfo.getUuid());
        UserRole one = userRoleService.getOne(userRoleLambdaQueryWrapper);

        if (Objects.isNull(one)) {
            throw new LoginException("用户没有权限登录，请联系管理员授权！");
        }

        // 判断用户权限
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getId, one.getRoleId());
        Role userRole = roleService.getOne(roleLambdaQueryWrapper);

        Long code = userRole.getId();

        boolean flag1 = Objects.equals(code, UserRoleEnum.ROOT.getCode());
        boolean flag2 = Objects.equals(code, UserRoleEnum.ADMIN.getCode());

        if (!(flag1 || flag2)) {
            throw new LoginException("用户没有权限登录，请联系管理员授权！");
        }

        return userInfo;
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public Page<UserInfo> getAll(int page, int pageSize) {

        // 构造分页构造器
        Page<UserInfo> userPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加排序条件  先按照修改时间排序，如果相同再按照uuid排序
        lambdaQueryWrapper.orderByAsc(UserInfo::getGmtModified).orderByDesc(UserInfo::getUuid);

        this.page(userPage, lambdaQueryWrapper);

        return userPage;
    }

    /**
     * 根据用户名模糊查询信息并返回
     *
     * @param page 页码
     * @param pageSize 每页展示的数据数
     * @param username 用户名，模糊查询的条件
     * @return
     */
    @Override
    public Page<UserInfo> getByUsername(int page, int pageSize, String username) {

        // 构造分页构造器
        Page<UserInfo> userPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(username), UserInfo::getUsername, username);
        // 将结果排序
        lambdaQueryWrapper.orderByDesc(UserInfo::getGmtModified);

        // 执行查询条件
        this.page(userPage, lambdaQueryWrapper);

        return userPage;
    }

    /**
     * 检查用户名唯一性
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo checkUsername(UserInfo userInfo) {

        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUsername, userInfo.getUsername());

        return userMapper.selectOne(lambdaQueryWrapper);
    }
}
