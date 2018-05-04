package com.nriet.framework.exceptions;
/**
 * 自定义异常类型及返回消息
 * @author b_wangpei
 *
 */
public enum ExceptionEnum {
	SUCCESS(200,"成功"),
    UNKNOW_ERROR(-1,"未知错误"),
    FAIL(400,"失败"),
    UNAUTHORIZED(401,"未认证（签名错误）"),
    NOT_FOUND(404,"请求的资源不存在"),
    REQUEST_NOT_SUPPORT(405,"服务器不允许的方法"),
    ARGUMENT_ERROR(108,"参数不合法"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
	RUNTIME_ERROR(1000,"服务器运行时异常"),
	NULL_POINT_ERROR(1001,"空指针异常：null"),
	CLASS_NOT_FIND_ERROR(1002,"找不到调用的类"),
	CLASS_CAST_ERROR(1003,"类型转换异常"),
	IO_ERROR(1004,"IO异常"),
	INDEX_OUT_OF_BOUNDS_ERROR(1005,"数组下标越界"),
	MISS_REQUEST_PARAM_ERROR(1006,"缺少必要的参数")
	;


    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
