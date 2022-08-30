package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.ProblemCase;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.ProblemCaseMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemCaseService;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  12:03
 * @Description: TODO
 */

@Service
public class ProblemCaseServiceImpl extends ServiceImpl<ProblemCaseMapper, ProblemCase> implements ProblemCaseService {
}
