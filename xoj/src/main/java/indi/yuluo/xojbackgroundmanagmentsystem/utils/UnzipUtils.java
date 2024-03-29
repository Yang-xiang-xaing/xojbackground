package indi.yuluo.xojbackgroundmanagmentsystem.utils;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-30  09:00
 * @Description: 解压压缩包工具类
 */

import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.Problem;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.domain.problem.ProblemCase;
import indi.yuluo.xojbackgroundmanagmentsystem.service.ProblemCaseService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class UnzipUtils {

    /**
     * 传文件绝对路径
     */
    public static void zipUncompress(String inputFile) {
        log.info("UnzipUtils开始解压: {}", inputFile);
        File oriFile = new File(inputFile);

        // 判断源文件是否存在
        String destDirPath = inputFile.replace(".zip", "");
        FileOutputStream fos = null;
        InputStream is = null;

        // 创建压缩文件对象
        try (ZipFile zipFile = new ZipFile(oriFile)) {

            // 开始解压
            Enumeration<?> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    oriFile.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    is = zipFile.getInputStream(entry);
                    fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }

                }
            }
        } catch (Exception e) {
            log.error("文件解压过程中异常", e);
        } finally {
            // 关流顺序，先打开的后关闭
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("文件流关闭异常", e);
            }
        }
        // 解压后删除文件
        if (oriFile.exists()) {
            System.gc();
            oriFile.delete();
            if (oriFile.exists()) {
                System.gc();
                oriFile.delete();
                if (oriFile.exists()) {
                    log.error("文件未被删除");
                }
            }
        }
        log.info("UnzipUtils解压完成");
    }
}


