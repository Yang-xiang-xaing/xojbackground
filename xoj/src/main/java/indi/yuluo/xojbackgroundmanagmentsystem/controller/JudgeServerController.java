package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.judge.JudgeServer;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.service.JudgeServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  11:01
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/judge")
@Api(value = "判题机数据控制器")
public class JudgeServerController {

    @Autowired
    private JudgeServerService judgeServerService;

    /**
     * 获取全部的判断机信息
     *
     * @param page      页码
     * @param pageSize  数据量
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有的数据信息", notes = "分页查询")
    public Result<?> getAll(int page, int pageSize) {
        Page<JudgeServer> all = judgeServerService.getAll(page, pageSize);

        return Result.success(all);
    }

    /**
     * 删除数据
     *
     * @param judgeServer 判题机对象
     * @return
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除判题机数")
    public Result<?> remove(@RequestBody JudgeServer judgeServer) {
        judgeServerService.removeById(judgeServer.getId());

        return Result.success("删除数据成功！");
    }

    /**
     * 添加数据
     *
     * @param judgeServer 判题机对象
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation(value = "添加数据")
    public Result<?> insert(@RequestBody JudgeServer judgeServer) {
        judgeServerService.save(judgeServer);

        return Result.success("添加判题机数据成功！");
    }

    /**
     * 修改数据
     *
     * @param judgeServer 判题机对象
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改判题机数据")
    public Result<?> update(@RequestBody JudgeServer judgeServer) {
        judgeServerService.updateById(judgeServer);

        return Result.success("修改数据成功");
    }

}
