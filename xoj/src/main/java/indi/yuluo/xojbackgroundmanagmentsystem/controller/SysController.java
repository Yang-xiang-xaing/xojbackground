package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-23  09:04
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/sys")
@Api(value = "服务监控数据控制器")
public class SysController {

    @Autowired
    private SysService sysService;

    /**
     * 获取java和操作系统信息
     *
     * @return
     */
    @GetMapping("/getJavaAndOSConfig")
    @ApiOperation(value = "获取java和操作系统信息")
    public Result<?> getJavaAndOSConfig() {

        Map<String, String> javaAndOSConfig = null;
        try {
            javaAndOSConfig = sysService.getJavaAndOSConfig();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return Result.success(javaAndOSConfig);
    }

    /**
     * 获取操作系统内存和jvm内存信息
     *
     * @return
     */
    @GetMapping("/getMemoryInfo")
    @ApiOperation(value = "获取操作系统内存和jvm内存信息")
    public Result<?> getMemoryInfo() {

        Map<String, String> memoryInfo = sysService.getMemoryInfo();

        return Result.success(memoryInfo);
    }


    /**
     * 获取磁盘信息
     *
     * @return
     */
    @GetMapping("/getDiskInfo")
    @ApiOperation(value = "获取操作系统磁盘信息")
    public Result<?> getDiskInformation() {
        List<Map<String, String>> diskInfoList = sysService.getDiskInfo();

        return Result.success(diskInfoList);
    }

    /**
     * 获取cpu信息
     *
     * @return
     */
    @GetMapping("/getCPUInfo")
    @ApiOperation(value = "获取CPU信息")
    public Result<?> getCPUInfo() {

        Map<String, String> CPUInfo = sysService.getCPUInfo();

        return Result.success(CPUInfo);
    }

}
