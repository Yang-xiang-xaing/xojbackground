package indi.yuluo.xojbackgroundmanagmentsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.service.impl.ProblemServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:31
 * @Description: TODO
 */

public interface ProblemService extends IService<Problem> {

    /**
     * 获取所有题目
     * @return
     */
    Page<Problem> getAll(int page, int pageSize);

    boolean uploadFile(MultipartFile file);
}
