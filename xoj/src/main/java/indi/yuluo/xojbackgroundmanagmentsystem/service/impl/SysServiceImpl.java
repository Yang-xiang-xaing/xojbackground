package indi.yuluo.xojbackgroundmanagmentsystem.service.impl;

import indi.yuluo.xojbackgroundmanagmentsystem.service.SysService;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;
import oshi.SystemInfo;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  13:54
 * @Description: TODO
 */

@Service
public class SysServiceImpl implements SysService {

    private static final SystemInfo systemInfo = new SystemInfo();
    private static final double num = 1.0 / 1024 / 1024;

    /**
     * 获取计算机和java运行环境信息
     *
     * @return
     */
    @Override
    public Map<String, String> getJavaAndOSConfig() throws UnknownHostException {

        Map<String, String> config = new HashMap<>();

        InetAddress adder = InetAddress.getLocalHost();
        Properties props = System.getProperties();

        String ip = adder.getHostAddress();
        String hostName = adder.getHostName();
        String javaVersion = props.getProperty("java.version");
        String javaVendor = props.getProperty("java.vendor");
        String javaHome = props.getProperty("java.home");
        String javaVMSpecificationVersion = props.getProperty("java.vm.specification.version");
        String javaVMVersion = props.getProperty("java.vm.version");
        String javaVMName = props.getProperty("java.vm.name");
        String javaSpecificationVersion = props.getProperty("java.specification.version");
        String OSName = props.getProperty("os.name");
        String OSArch = props.getProperty("os.arch");
        String OSVersion = props.getProperty("os.versio");

        config.put("ip", ip);
        config.put("hostName", hostName);
        config.put("javaVersion", javaVersion);
        config.put("javaVendor", javaVendor);
        config.put("javaHome", javaHome);
        config.put("javaVMSpecificationVersion", javaVMSpecificationVersion);
        config.put("javaVMVersion", javaVMVersion);
        config.put("javaVMName", javaVMName);
        config.put("javaSpecificationVersion", javaSpecificationVersion);
        config.put("OSName", OSName);
        config.put("OSName", OSArch);
        config.put("OSVersion", OSVersion);

        return config;
    }

    @Override
    public Map<String, String> getMemoryInfo() {

        Map<String, String> config = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = new Date(ManagementFactory.getRuntimeMXBean().getStartTime());

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        long initTotalMemorySize = memoryUsage.getInit();
        long maxMemorySize = memoryUsage.getMax();
        long usedMemorySize = memoryUsage.getUsed();
        String totalMemorySize = decimalFormat.format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
        String freePhysicalMemorySize = decimalFormat.format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
        long l = osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize();
        String usedMemory = decimalFormat.format(l / 1024.0 / 1024 / 1024) + "G";

        config.put("startDate", simpleDateFormat.format(startDate));
        config.put("totalMemoryJVM", decimalFormat.format(initTotalMemorySize * num) + "M");
        config.put("maxAvailableJVM", decimalFormat.format(maxMemorySize * num) + "M");
        config.put("usedJVM", decimalFormat.format(usedMemorySize * num) + "M");
        config.put("jvmMemoryUsedPercentage", new DecimalFormat("#.##%").format(usedMemorySize * 1.0 / initTotalMemorySize));
        config.put("totalMemorySize", totalMemorySize);
        config.put("freePhysicalMemorySize", freePhysicalMemorySize);
        config.put("usedMemory", usedMemory);
        config.put("physicsMemoryUsedPercentage", new DecimalFormat("#.##%").format(l * 1.0 / osmxb.getTotalPhysicalMemorySize()));


        return config;
    }


    /**
     * 磁盘使用情况
     *
     * @return
     */
    @Override
    public List<Map<String, String>> getDiskInfo() {

        List<Map<String, String>> list = new ArrayList<>();

        File[] files = File.listRoots();
        for (File file : files) {

            Map<String, String> config = new HashMap<>();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            String total = decimalFormat.format(file.getTotalSpace() * num / 1024) + "G";
            String un = decimalFormat.format(file.getUsableSpace() * num / 1024) + "G";
            long un1 = file.getTotalSpace() - file.getUsableSpace();
            String free = decimalFormat.format(un1 * num / 1024) + "G";
            String path = file.getPath();
            String used = new DecimalFormat("#.##%").format(un1 * 1.0 / file.getTotalSpace());

            // System.err.println(path + "总:" + total + ",可用空间:" + un + ",已用空间:" + free);
            // System.out.println("已用百分比 " + used);

            config.put("path", path);
            config.put("total", total);
            config.put("un", un);
            config.put("free", free);
            config.put("used", used);

            list.add(config);
        }

        return list;
    }

    /**
     * 打印 CPU 信息
     */
    @Override
    public Map<String, String> getCPUInfo() {

        Map<String, String> cpu = new HashMap<>();

        CentralProcessor processor = systemInfo.getHardware().getProcessor();

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[] ticks = processor.getSystemCpuLoadTicks();

        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];

        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        DecimalFormat decimalFormat = new DecimalFormat("#.##%");

        String used = decimalFormat.format(cSys * 1.0 / totalCpu);
        String userUsed = decimalFormat.format(user * 1.0 / totalCpu);
        String wait = decimalFormat.format(iowait * 1.0 / totalCpu);
        String free = decimalFormat.format(idle * 1.0 / totalCpu);

        cpu.put("cpuCoreNums", processor.getLogicalProcessorCount() + "");
        cpu.put("cpuUsedPercentage", used);
        cpu.put("cpuUserUsedPercentage", userUsed);
        cpu.put("cpuWaitPercentage", wait);
        cpu.put("cpuFreePercentage", free);

        return cpu;
    }
}
