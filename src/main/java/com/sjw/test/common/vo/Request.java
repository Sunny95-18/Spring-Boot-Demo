package com.sjw.test.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 15:07
 */
@Data
public class Request<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
}
