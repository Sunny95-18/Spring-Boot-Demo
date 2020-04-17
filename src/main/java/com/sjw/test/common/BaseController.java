package com.sjw.test.common;

import com.sjw.test.common.exceptions.SysExceptionEnum;
import com.sjw.test.common.exceptions.SystemException;
import com.sjw.test.common.vo.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 17:28
 */
@Controller
@ControllerAdvice
public class BaseController {

    protected <T> Response<T> success(T dataObject) {
        return new Response<>(dataObject);
    }

    protected  Response success(){
        return  new Response();
    }

    @ExceptionHandler
    public @ResponseBody Response exceptionHandler(HttpServletRequest request, HttpServletResponse httpResponse, Exception ex) {
        Response responseObject = new Response();
        ex.printStackTrace();
        try {

            if (ex instanceof SystemException) {//自定义异常提示
                SystemException systemException = (SystemException) ex;
                responseObject.setMessage(systemException.getMessage());
                responseObject.setCode(systemException.getCode());
                return responseObject;
            } else if (ex instanceof MethodArgumentNotValidException) {//数据验证错误提示
                responseObject.setCode(SysExceptionEnum.ILLEGAL_PARAM.getCode());
                String message = ex.getMessage();
                int start = message.lastIndexOf("default message");
                message = message.substring(start, message.length())//截取部分中文提示信息
                        .replace("default message", "").replace("]]", "]");//结尾处理
                responseObject.setMessage(SysExceptionEnum.ILLEGAL_PARAM.getMessage()
                        + message);
                return responseObject;
            } else if (ex instanceof DataAccessException) {//数据库操作异常
                responseObject.setMessage(SysExceptionEnum.SQL_ERROR.getMessage());
                responseObject.setCode(SysExceptionEnum.SQL_ERROR.getCode());
                return responseObject;
            } else if (ex instanceof NullPointerException) {//空指针的异常
                responseObject.setMessage(SysExceptionEnum.NULL_POINT_ERROR.getMessage());
                responseObject.setCode(SysExceptionEnum.NULL_POINT_ERROR.getCode());
                return responseObject;
            } else {
                responseObject.setMessage(SysExceptionEnum.INTERNAL_ERROR.getMessage());
                responseObject.setCode(SysExceptionEnum.INTERNAL_ERROR.getCode());
                return responseObject;
            }
        } catch (Exception e) {//其他异常
            responseObject.setMessage(SysExceptionEnum.INTERNAL_ERROR.getMessage());
            responseObject.setCode(SysExceptionEnum.INTERNAL_ERROR.getCode());
            return responseObject;
        }
    }

}
