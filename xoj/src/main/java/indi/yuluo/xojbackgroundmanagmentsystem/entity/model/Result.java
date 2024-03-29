package indi.yuluo.xojbackgroundmanagmentsystem.entity.model;

import indi.yuluo.xojbackgroundmanagmentsystem.Enum.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-26  14:53
 * @Description: 统一返回数据结果
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    // 编码
    private Integer code;

    // 错误信息
    private String message;

    // 数据
    private T data;

    /**
     * 只返回成功代码和描述，不返回其他数据
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 返回成功代码和描述，以及自定义数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 返回成功代码和自定义的String类型的信息描述和数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 返回失败的代码和描述信息，不带数据
     */
    public static Result<?> failed() {
        return new Result<>(ResultEnum.COMMON_FAILED.getCode(), ResultEnum.COMMON_FAILED.getMessage(), null);
    }


    /**
     * 返回失败的代码和自定义的String描述信息，不带数据
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultEnum.COMMON_FAILED.getCode(), message, null);
    }

    /**
     * 用于参数校验时，添加异常信息中的msg
     *
     * @param errorResult 继承IResult的枚举类
     * @param <T>         泛型
     * @return Result对象
     */
    public static <T> Result<T> failed(IResult errorResult, String message) {
        return new Result<>(errorResult.getCode(), message, null);
    }

    /**
     * 自定义选择结果枚举类中的信息
     *
     * @param errorResult 返回接口的具体实现类，通常是枚举
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(IResult errorResult) {
        return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
    }

    /**
     * 自定义返回信息
     *
     * @param code    代码
     * @param message 信息
     * @param data    数据
     * @param <T>     泛型
     * @return 返回中
     */
    public static <T> Result<T> instance(Integer code, String message, T data) {
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMessage(message);
        result.setData(data);

        return result;
    }

}
