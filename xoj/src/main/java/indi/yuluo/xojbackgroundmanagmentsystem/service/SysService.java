package indi.yuluo.xojbackgroundmanagmentsystem.service;

import oshi.SystemInfo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  13:54
 * @Description: TODO
 */

public interface SysService {

    /**
     * 获取计算机和java运行环境信息
     *
     * @return
     */
    Map<String, String> getJavaAndOSConfig() throws UnknownHostException;

    Map<String, String> getMemoryInfo();

    List<Map<String, String>> getDiskInfo();

    Map<String, String> getCPUInfo();
}
