package indi.yuluo.xojbackgroundmanagmentsystem.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.yuluo.xojbackgroundmanagmentsystem.Enum.ResultEnum;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import indi.yuluo.xojbackgroundmanagmentsystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-26  22:04
 * @Description: 请求拦截器
 */

@Slf4j
public class RequestCheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进入请求拦截器，校验token");

        response.setContentType("text/html; charset=utf-8");
        // OPTIONS为预检请求，直接放行
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }

        // 获取token
        String token = request.getHeader("Authorization");
        // 如果没有token直接拦截，返回错误信息
        if (Objects.isNull(token)) {
            // 这里直接用jackson
            log.info("token为空");
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(new Result<>().failed(ResultEnum.FORBIDDEN));
            response.getWriter().print(result);

            return false;
        }

        try {
            Claims claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            // token解析失败
            e.printStackTrace();
            log.info("token解析失败");
            // token不合法 拦截，返回错误信息
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(new Result<>().failed(ResultEnum.FORBIDDEN));
            response.getWriter().print(result);
            return false;
        }

        log.info("拦截器校验通过");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
