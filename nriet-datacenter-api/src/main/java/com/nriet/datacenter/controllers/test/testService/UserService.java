package com.nriet.datacenter.controllers.test.testService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nriet.framework.core.vo.Result;
import com.nriet.framework.core.vo.ResultGenerator;

@FeignClient(name = "manage-service",
				fallback = UserService.UserServiceFallback.class)
public interface UserService  {
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public Result test(); 
	
	@Component
    class UserServiceFallback implements UserService {
        private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFallback.class);
		@Override
		public Result test() {
			LOGGER.info("异常发生，进入fallback方法");
			return ResultGenerator.genFailResult("Userservice is not available !");
		}

    }
}
