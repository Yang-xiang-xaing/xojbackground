package indi.yuluo.xojbackgroundmanagmentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-22  22:44
 * @Description: TODO
 */

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
}
