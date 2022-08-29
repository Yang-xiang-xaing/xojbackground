package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.Role;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.RoleMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-23  10:52
 * @Description: TODO
 */

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
