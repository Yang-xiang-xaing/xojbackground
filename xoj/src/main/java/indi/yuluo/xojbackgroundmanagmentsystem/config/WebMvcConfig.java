package indi.yuluo.xojbackgroundmanagmentsystem.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import indi.yuluo.xojbackgroundmanagmentsystem.common.JacksonObjectMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.interceptor.RequestCheckTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yuluo
 * @FileName: WebMvcConfig.java
 * @createTime: 2022/5/21 10:16
 * @Description: 编写配置类来告诉mvc框架静态资源的位置在resources里
 */

@Slf4j
@Configuration
@EnableSwagger2  // 开启swagger文档功能
@EnableKnife4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加自定义的拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        ArrayList<String> ignorePath = new ArrayList<>();
        ignorePath.add("/user/login");
        ignorePath.add("/swagger-resources/**");
        ignorePath.add("/doc.html");
        ignorePath.add("/v2/**");
        ignorePath.add("/webjars/**");

        // 暂时屏蔽拦截器，测试系统功能
        /*registry.addInterceptor(new RequestCheckTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(ignorePath);*/
    }

    /**
     * 设置mvc静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 加入注解，方便调试
        log.info("开始进行静态资源映射……");

        // swagger接口文档映射
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 如果路径中包含/backend/ 就去resources里面的backend里面找
        // registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        // registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 扩展mvc的消息转换器
     * 为了解决json对于String字符串的序列化问题
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        log.info("扩展mvc框架的消息转换器……");

        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        // 设置对象转换器，底层使用jackson将java对象转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 将上面的对象追加到mvc框架的转换同容器中
        // 这是有先后顺序的，将咱们自己的转换器放在最前面
        converters.add(0, messageConverter);
    }

    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("indi.yuluo.xojbackgroundmanagmentsystem.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XOJ后台管理系统")
                .version("1.0.0")
                .contact(new Contact("yuluo", null, "yuluo829@aliyun.com"))
                .description("后台管理系统接口文档")
                .build();
    }

}
