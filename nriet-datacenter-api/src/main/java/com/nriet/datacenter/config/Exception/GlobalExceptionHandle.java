package com.nriet.datacenter.config.Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;
import com.nriet.framework.exceptions.ArgumentInvalidResult;
import com.nriet.framework.exceptions.ExceptionEnum;


@ControllerAdvice  
//如果返回的为json数据或其它对象，添加该注解  
@ResponseBody
public class GlobalExceptionHandle {
	//添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例  
	/**
	 * MethodArgumentNotValidException
	 * @param request
	 * @param exception
	 * @return
	 * @throws Exception
	 */
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
    /**
     * MethodArgumentTypeMismatchException
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=MethodArgumentTypeMismatchException.class)
    public Object MethodArgumentTypeMismatchException(HttpServletRequest request,  
    		MethodArgumentTypeMismatchException exception) throws Exception  
    {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();  
            invalidArgument.setDefaultMessage(exception.getMessage());  
            invalidArgument.setField(exception.getParameter().getParameterName());  
            invalidArgument.setRejectedValue(request.getParameter(exception.getParameter().getParameterName()));  
         
        return ResultGenerator.error(ExceptionEnum.ARGUMENT_ERROR)
        		.setData(invalidArgument);  
    }
    /**
     * HttpMessageNotReadableException
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=HttpMessageNotReadableException.class)  
    public Object HttpMessageNotReadableException(HttpServletRequest request,  
    		HttpMessageNotReadableException exception) throws Exception  
    {
//    		System.out.println(JSON.toJSONString(exception.getCause()));
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();  
            invalidArgument.setDefaultMessage(exception.getMessage());  
//            invalidArgument.setField(exception.get);  
            invalidArgument.setRejectedValue("");  
         
        return ResultGenerator.error(ExceptionEnum.FAIL)
        		.setData(invalidArgument);  
    }
//运行时异常
    @ExceptionHandler(RuntimeException.class)  
    @ResponseBody  
    public Result runtimeExceptionHandler(RuntimeException runtimeException) {  
        return ResultGenerator.error(ExceptionEnum.RUNTIME_ERROR)
        		.setData(runtimeException.getMessage());
    }  

//空指针异常
    @ExceptionHandler(NullPointerException.class)  
    @ResponseBody  
    public Result nullPointerExceptionHandler(NullPointerException ex) {  
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.NULL_POINT_ERROR)
        		.setData(ex.getMessage());
    }   
//类型转换异常
    @ExceptionHandler(ClassCastException.class)  
    @ResponseBody  
    public Result classCastExceptionHandler(ClassCastException ex) {  
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.CLASS_CAST_ERROR)
        		.setData(ex.getMessage());
    }

//IO异常
    @ExceptionHandler(IOException.class)  
    @ResponseBody  
    public Result iOExceptionHandler(IOException ex) {  
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.IO_ERROR)
        		.setData(ex.getMessage());
    }  
//未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)  
    @ResponseBody  
    public Result noSuchMethodExceptionHandler(NoSuchMethodException ex) {  
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.NOT_FOUND)
        		.setData(ex.getMessage());
    }  

//数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)  
    @ResponseBody  
    public Result indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {  
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.INDEX_OUT_OF_BOUNDS_ERROR)
        		.setData(ex.getMessage());
    }

//400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestParameterException ex){
        ex.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.FAIL)
        		.setData(ex.getMessage());
    }
//405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
    	e.printStackTrace();
    	return ResultGenerator.error(ExceptionEnum.REQUEST_NOT_SUPPORT)
        		.setData(e.getMessage());
    }
//
    /**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=Exception.class)  
    public Result allExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
        exception.printStackTrace();
        return ResultGenerator.error(ExceptionEnum.UNKNOW_ERROR);  
    }  

}
