package com.nriet.datacenter.config.logAspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.nriet.framework.core.BasicLogAspect;  
  
/**
 * 
 * @author b_wangpei
 * 
 */
@Aspect  
@Component  
public class LogAspect extends BasicLogAspect{

	@Override
	@Pointcut("execution(public * com.nriet.datacenter.controllers.*.*.*(..))")  
	public void webLog() {}

}
