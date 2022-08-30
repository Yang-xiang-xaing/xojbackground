package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:30
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/xoj/problem")
@Api(value = "题目信息控制器")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /**
     * 获取所有题目
     *
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有的题目信息", notes = "分页查询")
    public Result<?> getAll(int page, int pageSize) {

        Page<Problem> list = problemService.getAll(page, pageSize);

        return Result.success(list);
    }

    /**
     * 根据题目描述模糊查询题目
     *
     * @param page
     * @param pageSize
     * @param content
     * @return
     */
    @GetMapping("/getProblemByContent")
    @ApiOperation(value = "根据题目描述模糊查询题目", notes = "分页查询")
    public Result<?> getProblemByContent(int page, int pageSize, String content) {

        Page<Problem> problemPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Problem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(content), Problem::getTitle, content);

        Page<Problem> page1 = problemService.page(problemPage, lambdaQueryWrapper);

        return Result.success(page1);
    }

    /**
     * 删除题目数据
     *
     * @param problem
     * @return
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除题目数据")
    public Result<?> remove(@RequestBody Problem problem) {

        boolean res = problemService.removeById(problem.getId());

        return res ? Result.success("删除成功！") : Result.failed("删除失败！");
    }

    /**
     * 修改题目数据
     *
     * @param problem
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新题目数据")
    public Result<?> update(@RequestBody Problem problem) {

        problemService.updateById(problem);

        return Result.success("修改成功！");
    }

    /**
     * 添加题目数据 导入数据需为一个压缩包格式
     *
     * 题目测评文件的添加，加入题目数据时，加入相应的测评文件，一起作为参数传递到后端
     * 在后端进行解析，并将题目信息存入数据库，将测评文件创建和题目id相同的文件夹存放
     * 在判题或者导出文件时，一并读取文件夹的数据进行处理
     *
     * @param file 压缩包
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation(value = "添加题目数据")
    public Result<?> insertFile(@RequestPart("file") MultipartFile file) {

        boolean save = problemService.uploadFile(file);

        return save ? Result.success("添加题目数据成功！") : Result.failed("添加失败！");
    }

}
