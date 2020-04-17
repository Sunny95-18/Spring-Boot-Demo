package com.sjw.test.common.exceptions;


public class SystemException extends RuntimeException {

    private Long code ;

    private String message;


    public SystemException(Long code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
