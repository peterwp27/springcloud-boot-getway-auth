package cn.nriet.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.nriet.entity.User;
import cn.nriet.manage.service.UserService;

@RefreshScope
@RestController
public class UserController {

    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Value("${msg:unknown}")
    private String msg;
    
    @Value("${queryUrl.testUrl:unknown}")
    private String testUrl;
    
    @Autowired
    private UserService uservice;

	@GetMapping(value = "/validate/{name}/{password}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User validate(@PathVariable String name, @PathVariable String password) {
		User u = new User();
		u = uservice.findByNameAndPassowrd(name, password);
		return u;
	}
	@RequestMapping(value={"/save"}, method=RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User save(@RequestBody User u) {
		System.out.println("#####user.name:::"+u.getUsername()+";user.password:::"+u.getPassword());
		return uservice.save(u);
	}
	@GetMapping(value = "/findall",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAll() {
		return uservice.findAll();
	}
	@RequestMapping(value = "/user/{name}/{password}", method = RequestMethod.GET) 
    public String printServiceB(@PathVariable("name") String name,@PathVariable("password") String password) {
    	System.out.println(testUrl);
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say " + password;
    }
}