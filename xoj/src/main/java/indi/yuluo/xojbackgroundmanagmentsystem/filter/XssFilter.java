package indi.yuluo.xojbackgroundmanagmentsystem.filter;

import indi.yuluo.xojbackgroundmanagmentsystem.common.XssHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-26  22:17
 * @Description: 防止Xss攻击
 */

@WebFilter("/**")
@Slf4j
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 这个过滤器的作用，仅仅是将原始的request进行一个包装，通过子类去处理恶意标签
        log.info("进入Xss Filter");
        filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest),servletResponse);
    }
}
