package indi.yuluo.xojbackgroundmanagmentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.ProblemCase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  12:03
 * @Description: TODO
 */

@Mapper
@Repository
public interface ProblemCaseMapper extends BaseMapper<ProblemCase> {
}
