package indi.yuluo.xojbackgroundmanagmentsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.judge.JudgeServer;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  11:02
 * @Description: TODO
 */

public interface JudgeServerService extends IService<JudgeServer> {

    Page<JudgeServer> getAll(int page, int pageSize);
}
