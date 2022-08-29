package indi.yuluo.xojbackgroundmanagmentsystem;

import indi.yuluo.xojbackgroundmanagmentsystem.service.SysService;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.SigarException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-27  15:33
 * @Description: TODO
 */

@Slf4j
@SpringBootTest
public class TestUtils {

    @Autowired
    private SysService sysService;

    @Test
    void testMd5() {
        String data = "123";
        String res = MD5Util.getMD5(data);
        log.info(res);
    }

    @Test
    void testSys() throws UnknownHostException, SigarException {
        // List<Map<String, String>> diskInfo = sysService.getDiskInfo();
        // for (Map<String, String> map : diskInfo) {
        //     System.out.println(map);
        // }

        Map<String, String> cpuInfo = sysService.getCPUInfo();
        System.out.println(cpuInfo);
        //
        // Map<String, String> memoryInfo = sysService.getMemoryInfo();
        // System.out.println(memoryInfo);
        //
        // Map<String, String> javaAndOSConfig = sysService.getJavaAndOSConfig();
        // System.out.println(javaAndOSConfig);
    }


}
