package com.nriet.framework.core;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;  
  
/**
 * 日志AOP拦截器
 * 2018-4-9
 * @author b_wangpei
 * 
 */
public abstract class BasicLogAspect {
	private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Pointcut("execution(public * cn.nriet.manage.controller.*.*(..))")  
	/**
	 * 必需实现@Pointcut注解
	 * 配置对应的需要切入的方法群；例如("execution(public * cn.nriet.manage.controller.*.*(..)) or execution(...)")--切入所有public属性的，cn.nriet.manage.controller包下的所有方法
	 */
    public abstract void webLog();
    //方法开始切入
    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容  
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();  
        String url = request.getRequestURL().toString();
        logger.info("请求开始--- " + url + " METHOD: [ " + request.getMethod() + " ]");
        logger.info("请求参数--- " + url.substring(url.lastIndexOf("/") + 1) + " : " +  Arrays.toString(joinPoint.getArgs()));
    }
    //方法结束切入
    @AfterReturning(returning = "ret", pointcut = "webLog()")  
    public void doAfterReturning(Object ret) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();  
        String url = request.getRequestURL().toString();
        // 处理完请求，返回内容  
    	logger.info("方法结束--- " + url.substring(url.lastIndexOf("/") + 1) + " :  返回值 --> " + ret + " 长度-->[ " + JSON.toJSONString(ret).length() +" ]");
    }  
  
    //后置异常通知 
    @AfterThrowing("webLog()")  
    public void throwss(JoinPoint jp){  
    	
        System.out.println("方法异常时执行.....");  
    }  
  
    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行  
    @After("webLog()")  
    public void after(JoinPoint jp){  
//        System.out.println("方法最后执行.....");  
    }  
  
    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")  
    public Object arround(ProceedingJoinPoint pjp) {  
        try {
            Object o =  pjp.proceed();    
            return o;
        } catch (Throwable e) {
//        	if(e instanceof DescribeException){
//                DescribeException pingjiaException = (DescribeException) e;
//                return ResultGenerator.error(pingjiaException);
//            }
//
//        	logger.error("【系统异常】{}",e);
//            return ResultGenerator.error(ExceptionEnum.UNKNOW_ERROR); 
        	return null;
        }
    }  
}  
