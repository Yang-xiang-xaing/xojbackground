package indi.yuluo.xojbackgroundmanagmentsystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@ServletComponentScan  // 使得设置的过滤器生效
@EnableTransactionManagement  // 开启事务支持
@EnableOpenApi
@SpringBootApplication
public class XojApplication {

    public static void main(String[] args) {

        SpringApplication.run(XojApplication.class, args);
        log.info("XOJ后台管理系统启动……");
    }

}
