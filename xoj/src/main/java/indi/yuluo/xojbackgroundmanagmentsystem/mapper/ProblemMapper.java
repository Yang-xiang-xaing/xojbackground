package indi.yuluo.xojbackgroundmanagmentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:32
 * @Description: TODO
 */

@Mapper
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {
}
