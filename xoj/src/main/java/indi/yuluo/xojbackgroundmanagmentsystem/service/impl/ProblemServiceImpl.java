package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.ProblemMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemService;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.UnzipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.json.JSONUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:31
 * @Description: TODO
 */

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Value("${file-server.path}")
    private String uploadPath;

    @Autowired
    private ProblemMapper problemMapper;

    /**
     * 获取所有题目
     *
     * @return
     */
    @Override
    public Page<Problem> getAll(int page, int pageSize) {

        Page<Problem> problemPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Problem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Problem::getProblemId).orderByAsc(Problem::getGmtModified);

        return this.page(problemPage, lambdaQueryWrapper);
    }

    /**
     * 处理题目压缩包数据
     *
     * @param file 压缩包
     * @return 布尔值表示成功或者失败
     */
    @Override
    public boolean uploadFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();// 原始名称
        if (StringUtils.isBlank(originalFilename) || !originalFilename.endsWith(".zip")) {
            return false;
        }

        String newName = UUID.randomUUID().toString().replace("-", "");// uuid作为文件夹新名称,不重复
        String zipName = newName + ".zip";// uuid作为压缩文件新名称,不重复

        InputStream inputStream;// 文件流
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 检测创建文件夹
        Path directory = Paths.get(uploadPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Files.copy(inputStream, directory.resolve(zipName));// 上传文件，返回值是文件大小
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String zipPath = uploadPath + File.separator + zipName;
        Problem problem = UnzipUtils.zipUncompress(zipPath);

        // 保存题目到数据库中
        boolean save = this.save(problem);

        // 并且将题目的测评文件存储到problem_case表中


        return true;
    }

    /**
     * 智能识别补全路径
     *
     * @return
     */
    private String getUploadPath() {
        Properties props = System.getProperties();
        String property = props.getProperty("os.name");
        String userHomePath = props.getProperty("user.home");
        String filePath = "";// 文件存放地址
        if (property.contains("Windows")) {
            String[] arr = userHomePath.split(":");
            String pan = arr[0] + ":";// windows会取第一个盘盘符
            filePath = pan + uploadPath;
        } else if (property.contains("Linux")) {
            filePath = uploadPath;
        }
        return filePath;
    }

}
