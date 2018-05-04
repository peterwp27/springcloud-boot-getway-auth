package com.nriet.framework.core.vo;

import com.nriet.framework.exceptions.DescribeException;
import com.nriet.framework.exceptions.ExceptionEnum;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result<?> genSuccessResult() {
        return new Result()
                .setCode(ExceptionEnum.SUCCESS.getCode())
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result<Object> genSuccessResult(Object data) {
        return new Result<Object>()
                .setCode(ExceptionEnum.SUCCESS.getCode())
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result<Object> genFailResult(String message) {
        return new Result<Object>()
                .setCode(ExceptionEnum.FAIL.getCode())
                .setMessage(message);
    }

    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Result<?> error(Integer code,String msg){
        return new Result()
        		.setCode(code)
        		.setMessage(msg);
    }
    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Result<?> error(ExceptionEnum e){
        return new Result()
        		.setCode(e.getCode())
        		.setMessage(e.getMsg());
    }
    /**
     * 返回异常信息，在已知的范围内
     * @param DescribeException
     * @return
     */
    public static Result<?> error(DescribeException exception){
        return new Result().setFail(exception);
    }
}
