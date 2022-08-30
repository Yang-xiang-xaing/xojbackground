package indi.yuluo.xojbackgroundmanagmentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.discussion.Discussion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  09:26
 * @Description: TODO
 */

@Mapper
@Repository
public interface DiscussionMapper extends BaseMapper<Discussion> {
}
