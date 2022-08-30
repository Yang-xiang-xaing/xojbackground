package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.ProblemCase;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.mapper.ProblemMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemCaseService;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemService;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.UnzipUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

import static indi.yuluo.xojbackgroundmanagmentsystem.utils.FileUtils.check;
import static indi.yuluo.xojbackgroundmanagmentsystem.utils.FileUtils.getJsonFile;
import static indi.yuluo.xojbackgroundmanagmentsystem.utils.JSONUtils.getBean;
import static indi.yuluo.xojbackgroundmanagmentsystem.utils.JSONUtils.readJsonFile;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:31
 * @Description: TODO
 */

@Service
@Slf4j
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Value("${file-server.path}")
    private String uploadPath;

    @Autowired
    private ProblemCaseService problemCaseService;

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

        log.info(String.valueOf(file));

        String originalFilename = file.getOriginalFilename();// 原始名称
        if (StringUtils.isBlank(originalFilename) || !originalFilename.endsWith(".zip")) {
            return false;
        }

        String newName = UUID.randomUUID().toString().replace("-", "");// uuid作为文件夹新名称,不重复
        log.info("压缩包新名字{}", newName);
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

        // 调用工具类进行解压
        UnzipUtils.zipUncompress(zipPath);

        // 从解压之后的文件中筛选出来.json后缀名的题目数据
        List<String> jsonFile = getJsonFile(uploadPath + File.separator + newName);

        // 存放题目对象
        List<Problem> list = new ArrayList<>();
        List<String> folderName = new ArrayList<>();

        for (String s : jsonFile) {

            System.err.println(s);

            // 将json文件转换为problem对象
            String content = readJsonFile(s);
            list.add(getBean(content));
            // 截取和题目对应的测评文件夹
            folderName.add(s.substring(s.lastIndexOf("\\") + 1).split("\\.")[0]);
        }

        // 查询解压之后的压缩包有没有和题目problem_id同名的测评数据文件夹 如果有，移动到父级目录中，并且删除当前解压的压缩包
        check(folderName, uploadPath + File.separator + newName);

        // 存储题目
        boolean b = this.saveBatch(list);

        // 并且将题目的测评文件存储到problem_case表中
        this.insert(list);

        return b;
    }

    /**
     * 在插入题目时，插入和题目对应的评测数据地址
     *
     * @param list
     */
    private void insert(List<Problem> list) {

        List<ProblemCase> collect = list.stream().map((item) -> {

            String path = item.getProblemId();

            ProblemCase problemCase = new ProblemCase();

            LambdaQueryWrapper<Problem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Problem::getProblemId, path);
            Problem one = this.getOne(lambdaQueryWrapper);

            problemCase.setPid(one.getId());
            problemCase.setFolderPath(path);

            return problemCase;
        }).collect(Collectors.toList());

        problemCaseService.saveBatch(collect);
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
