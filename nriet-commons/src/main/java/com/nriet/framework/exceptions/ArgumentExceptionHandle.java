package com.nriet.framework.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nriet.framework.core.vo.ResultGenerator;

@ControllerAdvice  
//如果返回的为json数据或其它对象，添加该注解  
@ResponseBody
/**
 * 参数异常拦截方法
 * @author b_wangpei
 *
 */
public class ArgumentExceptionHandle {
	//添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例  
    @ExceptionHandler(value=MethodArgumentNotValidException.class)  
    public Object MethodArgumentNotValidHandler(HttpServletRequest request,  
            MethodArgumentNotValidException exception) throws Exception  
    {
        //按需重新封装需要返回的错误信息  
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();  
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息  
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {  
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();  
            invalidArgument.setDefaultMessage(error.getDefaultMessage());  
            invalidArgument.setField(error.getField());  
            invalidArgument.setRejectedValue(error.getRejectedValue());  
            invalidArguments.add(invalidArgument);
        }
         
        return ResultGenerator.error(ExceptionEnum.ARGUMENT_ERROR)
        		.setData(invalidArguments);  
    }  
}
