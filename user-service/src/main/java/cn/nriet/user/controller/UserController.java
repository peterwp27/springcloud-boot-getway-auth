package cn.nriet.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.nriet.entity.User;
import cn.nriet.user.service.UserService;

@RefreshScope
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    EurekaDiscoveryClient discoveryClient;

	@GetMapping(value = "/validate/{name}/{password}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User validate(@PathVariable String name, @PathVariable String password) {
		return userService.findByNameAndPassowrd(name, password);
	}
	
	@GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say UserService";
    }
}
