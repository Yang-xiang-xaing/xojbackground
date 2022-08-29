package indi.yuluo.xojbackgroundmanagmentsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.discussion.Discussion;

import java.util.List;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  09:26
 * @Description: TODO
 */

public interface DiscussionService extends IService<Discussion> {

    /**
     * 获取全部评论
     * @return
     */
    Page<Discussion> getAll(int page, int pageSize);

    Page<Discussion> getDiscussionByContent(int page, int pageSize, String content);
}
