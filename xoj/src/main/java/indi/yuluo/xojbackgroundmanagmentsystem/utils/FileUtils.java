package indi.yuluo.xojbackgroundmanagmentsystem.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  15:22
 * @Description: TODO
 */

@Slf4j
public class FileUtils {

    private static List<String> jsonList = new ArrayList<>();

    /**
     * @param path 路径
     */
    public static List<String> getJsonFile(String path) {

        log.info("getJsonFile:::{}", path);

        File file = new File(path);

        // 获得此路径下的所有文件
        File[] FileNames = file.listFiles();

        // 对获得文件名称进行判断，如果是json文件 加入list中
        if (FileNames != null) {
            for (File fileNames : FileNames) {
                String curPath = fileNames.getPath();
                if (curPath.endsWith(".json")) {
                    log.info("getJsonFile中的curPath: {}", curPath);
                    jsonList.add(curPath);
                }
            }
        } else {
            throw new RuntimeException("获取文件失败！");
        }

        return jsonList;
    }

    /**
     * 查询解压之后的压缩包有没有和题目problem_id同名的测评数据文件夹
     * 如果有，移动到父级目录中，并且删除当前解压的压缩包
     *
     * @param folderName json文件名
     * @param path       当前解压出来的文件夹路径
     */
    public static void check(List<String> folderName, String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        String basePath = path.substring(0, path.lastIndexOf("\\") + 1);

        assert files != null;
        for (File file1 : files) {
            // System.out.println(file1);
            if (file1.isDirectory() && folderName.contains(file1.getName())) {
                File file2 = new File(basePath + file1.getName());
                try {
                    move(file1, file2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        del(new File(path));
    }


    /**
     * 移动文件
     *
     * @param file
     * @param file1
     * @throws IOException
     */
    private static void move(File file, File file1) throws IOException {

        if (!file1.exists()) {
            file1.mkdirs();
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    move(file2, new File(file1.getPath(), file2.getName()));
                } else {
                    Files.copy(file2.toPath(), Paths.get(file1.getPath() + "\\" + file2.getName()));
                }
            }
        }
    }


    /**
     * 移除解压文件夹
     */
    private static void del(File file) {

        File[] list = file.listFiles();  //无法做到list多层文件夹数据
        if (list != null) {
            for (File temp : list) {     //先去递归删除子文件夹及子文件
                del(temp);   //注意这里是递归调用
            }
        }

        if (!file.delete()) {     //再删除自己本身的文件夹
            throw new RuntimeException("删除文件夹失败！");
        }
    }

}


