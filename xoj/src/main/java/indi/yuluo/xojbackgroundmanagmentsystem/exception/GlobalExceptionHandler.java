package indi.yuluo.xojbackgroundmanagmentsystem.exception;

import indi.yuluo.xojbackgroundmanagmentsystem.Enum.ResultEnum;
import indi.yuluo.xojbackgroundmanagmentsystem.entity.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

/**
 * @Author: yuluo
 * @CreateTime: 2022-07-21  11:37
 * @Description: 全局异常处理
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})   // 指定拦截的控制器
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 用户不存在或者密码输入错误异常
     *
     * @param loginException 登录异常
     * @return
     */
    @ExceptionHandler({LoginException.class})
    public Result<?> handleLoginException(LoginException loginException) {
        log.info(loginException.getMessage());
        return Result.failed(loginException.getMessage());
    }

    /**
     * 参数校验不通过时抛出的异常处理
     *
     * @param ex 异常对象
     * @return 结果
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        // 如果有异常提示信息时使用
        if (StringUtils.hasText(msg)) {
            log.info(msg);
            return Result.failed(ResultEnum.VALIDATE_FAILED, msg);
        }
        return Result.failed(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 参数校验不通过时抛出的异常处理
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<?> handleConstraintViolationException(ConstraintViolationException ex) {
        if (StringUtils.hasText(ex.getMessage())) {
            log.info(ex.getMessage());
            return Result.failed(ResultEnum.VALIDATE_FAILED, ex.getMessage());
        }
        return Result.failed(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
     */
    @ExceptionHandler({Exception.class})
    public Result<?> handle(Exception ex) {
        return Result.failed(ex.getMessage());
    }

}
