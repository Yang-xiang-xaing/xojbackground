package indi.yuluo.xojbackgroundmanagmentsystem;

import indi.yuluo.xojbackgroundmanagmentsystem.utils.StrongPasswordGenerationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XojApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGenericPassword() {
        String s = StrongPasswordGenerationUtil.DigitGenerate(20);
        System.out.println(s);
    }

}
