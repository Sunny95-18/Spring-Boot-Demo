package com.sjw.test.common.vo;

import com.sjw.test.common.exceptions.SysExceptionEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 15:03
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long code=200L;

    private String message="请求成功";
    private T data=null;
    private String display=null;

    public Response() {
    }
    public Response(T data) {
        this.data = data;
    }
    public Response(Long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.display = null;
    }
    public Response(Long code, String message, T data, String display) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.display = display;
    }


    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Response<T> success(T data) {
        return new Response<T>(200L, "请求成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> Response<T> success(T data, String message) {
        return new Response<T>(200L, message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> Response<T> failed(SysExceptionEnum errorCode) {
        return new Response<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> Response<T> failed(String message) {
        return new Response<T>(SysExceptionEnum.INTERNAL_ERROR.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Response<T> failed() {
        return failed(SysExceptionEnum.INTERNAL_ERROR.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Response<T> validateFailed() {
        return failed(SysExceptionEnum.VALIDATE_FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> Response<T> validateFailed(String message) {
        return new Response<T>(SysExceptionEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Response<T> unauthorized(T data) {
        return new Response<T>(SysExceptionEnum.UNAUTHORIZED.getCode(), SysExceptionEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Response<T> forbidden(T data) {
        return new Response<T>(SysExceptionEnum.FORBIDDEN.getCode(), SysExceptionEnum.FORBIDDEN.getMessage(), data);
    }
}
