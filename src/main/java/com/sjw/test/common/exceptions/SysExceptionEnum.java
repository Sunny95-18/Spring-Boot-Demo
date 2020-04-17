package com.sjw.test.common.exceptions;

/**
 * Created by rongmc on 16/3/23.
 *
 */
public enum SysExceptionEnum {

    /****************************/
    /***** 5XX 系统异常提示  *****/
    /****************************/

    NO_PRIVILEGE(401L, "无权限访问接口"),
    UNAUTHORIZED(402L, "暂未登录或token已经过期"),
    FORBIDDEN(403L, "没有相关权限"),
    VALIDATE_FAILED(404L, "参数检验失败"),
    INTERNAL_ERROR(500L, "系统内部异常"),
    NULL_POINT_ERROR(501L,"数据空异常"),
    APP_KEY_ERROR(502L, "非法应用,AppKey错误"),
    SIGN_ERROR(503L, "签名错误"),
    ILLEGAL_PARAM(504L, "请求参数不合法"),
    NO_LOGIN(505L, "未登录"),
    SQL_ERROR(508L, "数据库操作异常,字段不合格或者主键冲突等!"),

    //100XXX 自定义异常
    USER_IS_NOT_EXIST(100001L,"用户不存在"),
    PASSWORD_ERROR(100002L,"用户密码错误")
    ;



    private Long code;
    private String message;


    SysExceptionEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SysExceptionEnum get(Long code) {
        if(code == null) {
            return null;
        } else {
            SysExceptionEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                SysExceptionEnum appResultCodeEnum = arr$[i$];
                if(appResultCodeEnum.getCode().equals(code)) {
                    return appResultCodeEnum;
                }
            }

            return null;
        }
    }

    public static boolean is(SysExceptionEnum appResultCodeEnum, Long code) {
        return appResultCodeEnum != null && code != null?appResultCodeEnum.getCode().equals(code):false;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
