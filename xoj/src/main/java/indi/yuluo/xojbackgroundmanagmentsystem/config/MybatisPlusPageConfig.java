package indi.yuluo.xojbackgroundmanagmentsystem.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yuluo
 * @FileName: MybatisPlusPageConfig.java
 * @createTime: 2022/5/22 11:24
 * @Description: MP分页插件的配置类 通过拦截器实现
 */

@Configuration
@MapperScan("indi.yuluo.xojbackgroundmanagmentsystem.mapper")
public class MybatisPlusPageConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return mybatisPlusInterceptor;
    }

}
