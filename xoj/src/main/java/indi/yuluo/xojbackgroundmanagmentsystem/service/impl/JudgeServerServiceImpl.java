package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.judge.JudgeServer;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.JudgeServerMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.JudgeServerService;
import org.springframework.stereotype.Service;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  11:02
 * @Description: TODO
 */

@Service
public class JudgeServerServiceImpl extends ServiceImpl<JudgeServerMapper, JudgeServer> implements JudgeServerService {

    /**
     * 获取所有的判断机信息
     *
     * @return
     */
    @Override
    public Page<JudgeServer> getAll(int page, int pageSize) {

        Page<JudgeServer> judgeServerPage = new Page<JudgeServer>(page, pageSize);

        LambdaQueryWrapper<JudgeServer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(JudgeServer::getId);

        this.page(judgeServerPage, lambdaQueryWrapper);

        return judgeServerPage;
    }
}
