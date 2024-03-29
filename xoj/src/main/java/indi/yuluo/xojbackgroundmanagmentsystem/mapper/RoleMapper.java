package indi.yuluo.xojbackgroundmanagmentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-23  10:51
 * @Description: TODO
 */

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
