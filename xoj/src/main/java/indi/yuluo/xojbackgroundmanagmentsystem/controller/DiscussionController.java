package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.discussion.Discussion;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.service.DiscussionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  09:24
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/xoj/discussion")
@Api(value = "XOJ讨论信息控制器")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/getAll")
    @ApiOperation(value = "获取全部评论", notes = "分页显示")
    public Result<?> getAll(int page, int pageSize) {

        Page<Discussion> list = discussionService.getAll(page, pageSize);

        return Result.success(list);
    }

    /**
     * 根据评论内容模糊查询评论
     *
     * @param page     页码
     * @param pageSize 数据量
     * @param content  评论内容
     * @return page对象
     */
    @GetMapping("/getDiscussionByContent")
    @ApiOperation(value = "根据评论内容模糊查询评论", notes = "分页查询")
    public Result<?> getDiscussionByContent(int page, int pageSize, String content) {

        Page<Discussion> list = discussionService.getDiscussionByContent(page, pageSize, content);

        return Result.success(list);
    }

    /**
     * 修改评论信息
     *
     * @param discussion
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改评论信息")
    public Result<?> updateDiscussionById(@RequestBody Discussion discussion) {

        discussionService.updateById(discussion);

        return Result.success("评论信息修改成功！");
    }

    /**
     * 逻辑删除评论
     *
     * @param discussion
     * @return
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除评论", notes = "逻辑删除")
    public Result<?> delDiscussion(@RequestBody Discussion discussion) {

        // 将status的状态值改为1，表示逻辑删除状态
        discussion.setStatus(1);

        discussionService.updateById(discussion);

        return Result.success("删除评论成功！");
    }

}
