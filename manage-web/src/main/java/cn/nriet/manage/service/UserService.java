package cn.nriet.manage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.liandi.framework.core.BasicController;
import cn.liandi.framework.core.BasicService;
import cn.nriet.entity.User;
import feign.Feign;
import feign.Headers;

@FeignClient(name = "manage-service", fallback = UserService.UserServiceFallback.class)
public interface UserService  {
	
	

	@Headers("Authorization: Bearer  {acc_tocken}")
	@RequestMapping(method = RequestMethod.GET, value = "/validate/{name}/{password}")
	User validate(@PathVariable("acc_tocken") String acc_tocken,@PathVariable("name") String name,
			@PathVariable("password") String password);
	
	@Component
    class UserServiceFallback implements UserService {

        private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFallback.class);

		@Override
		public User validate(String acc_tocken,String name, String password) {
			LOGGER.info("异常发生，进入fallback方法");
			return null;
		}

    }
}
