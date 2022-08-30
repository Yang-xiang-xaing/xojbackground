package indi.yuluo.xojbackgroundmanagmentsystem.utils;


import cn.hutool.json.JSONUtil;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;


/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  10:46
 * @Description: TODO
 */

@Slf4j
public class JSONUtils {


    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {

        String jsonStr = "";
        FileReader fileReader = null;
        Reader reader = null;
        File jsonFile;
        try {
            log.info("readJsonFile中fileName: {}", fileName);
            jsonFile = new File(fileName);
            log.info("readJsonFile中的文件名：{}", jsonFile);
            fileReader = new FileReader(jsonFile);

            reader = new InputStreamReader(Files.newInputStream(jsonFile.toPath()),"utf-8");

            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();

            // 读取完毕之后删除jsonfile文件
            jsonFile.delete();

            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                assert fileReader != null;
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 将json串转换为对象
     *
     * @return
     */
    public static Problem getBean(String content) {

        return JSONUtil.toBean(content, Problem.class);
    }

}
