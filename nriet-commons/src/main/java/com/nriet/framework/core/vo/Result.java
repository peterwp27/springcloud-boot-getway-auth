package com.nriet.framework.core.vo;

import com.nriet.framework.exceptions.DescribeException;

/**
 * 统一API响应结果封装
 */
public class Result<T extends Object> {
    private int code;
    private String message;
    private T data;

    public static Result<?> createInstance() {
    	return ResultGenerator.genSuccessResult();
    }
    
    public Result<T> setFail(DescribeException result) {
        this.code = result.getCode();
        this.message = result.getMessage();
        return this;
    }
    
    public Result<T> setCode(int resultCode) {
        this.code = resultCode;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = (T) data;
        return this;
    }
   
}
