package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.discussion.Discussion;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.user.UserInfo;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.DiscussionMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.DiscussionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  09:28
 * @Description: TODO
 */

@Service
public class DiscussionServiceImpl extends ServiceImpl<DiscussionMapper, Discussion> implements DiscussionService {

    /**
     * 获取全部评论
     *
     * @return
     */
    @Override
    public Page<Discussion> getAll(int page, int pageSize) {

        Page<Discussion> discussionPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Discussion> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加排序条件  先按照修改时间排序，如果相同再按照uuid排序
        lambdaQueryWrapper.orderByAsc(Discussion::getId).orderByDesc(Discussion::getGmtModified);

        this.page(discussionPage, lambdaQueryWrapper);

        return discussionPage;
    }

    /**
     * 根据评论内容模糊查询评论
     *
     * @param page
     * @param pageSize
     * @param content
     * @return
     */
    @Override
    public Page<Discussion> getDiscussionByContent(int page, int pageSize, String content) {

        Page<Discussion> discussionPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Discussion> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(content), Discussion::getContent, content);
        lambdaQueryWrapper.orderByDesc(Discussion::getGmtModified).orderByAsc(Discussion::getId);

        this.page(discussionPage, lambdaQueryWrapper);

        return discussionPage;
    }
}
