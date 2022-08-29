package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.ProblemMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemService;
import org.springframework.stereotype.Service;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:31
 * @Description: TODO
 */

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
}
