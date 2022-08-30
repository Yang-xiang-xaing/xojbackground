package indi.yuluo.xojbackgroundmanagmentsystem.utils;


import cn.hutool.json.JSONUtil;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;

import java.io.*;


/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  10:46
 * @Description: TODO
 */

public class JSONUtils {


    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
