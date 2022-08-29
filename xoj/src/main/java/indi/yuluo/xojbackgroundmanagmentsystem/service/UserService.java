package indi.yuluo.xojbackgroundmanagmentsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-22  22:44
 * @Description: TODO
 */

public interface UserService extends IService<UserInfo> {

    /**
     * 根据用户名查询用户并且判断用户权限
     *
     * @param username
     * @param password
     * @return
     */
    UserInfo getUserByUserNameAndCheckUserRole(String username, String password);

    /**
     * 获取所有用户
     * @return
     */
    Page<UserInfo> getAll(int page, int pageSize);

    Page<UserInfo> getByUsername(int page, int pageSize, String username);

    /**
     * 检查用户名唯一性
     *
     * @param userInfo
     * @return
     */
    UserInfo checkUsername(UserInfo userInfo);
}
