package cn.nriet.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.liandi.framework.core.BasicController;
import cn.nriet.entity.User;
import cn.nriet.manage.service.ITestService;

@Controller
@RefreshScope
//@RequestMapping("/manage-web")
public class ManageController extends BasicController {
	
	@Autowired
	private ITestService iTestService;

	
    @Value("${queryUrl.loginUrl:unknown}")
    private String loginUrl;
    
    @Value("${queryUrl.ctx:''}")
    private String ctx;
    
//	@Autowired  
//    private RestTemplate restTemplate;  
	@Autowired
    EurekaDiscoveryClient discoveryClient;
	@RequestMapping(value = "/login")
	public String login(@ModelAttribute User loginUser, Model model,
			HttpSession session) {
//		System.out.println("进入login");
		model.addAttribute("ctx",ctx);
		if (loginUser.getUsername() == null) {
			model.addAttribute("message","hahaha");
			return "login";
		}
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("name", loginUser.getUsername());
		parameterMap.put("password", loginUser.getPassword());
		// 调用验证服务
		User dataUser = iTestService.getForObject(loginUrl, User.class,parameterMap);
		if (dataUser.getId() == null) {
			// 验证失败
			model.addAttribute("message", "fail");
			return "login";
		} else {
			session.setAttribute("manage-user", loginUser);
			return "index";
		}
	}
	
	@RequestMapping(value="/test")
	public String test(Model model){
		model.addAttribute("ctx",ctx);
		String url = "http://localhost:8060/manage/save";
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("id", 11);
		parameterMap.put("username", "user");
		parameterMap.put("password", "password");
		User re = iTestService.postForEntity(url, User.class, parameterMap);
		System.out.println("@@@@@@@@@@@@@"+re.getPassword());
		return "login";
	}
	@RequestMapping(value="/findall")
	public String findAll(Model model){
		model.addAttribute("ctx",ctx);
		String url = "http://localhost:8060/manage/findall";
		Map<String, Object> parameterMap = new HashMap<>();
//		User re = iTestService.postForEntity(url, User.class, null);
		List<User> list = iTestService.getForList(url,parameterMap);
		for(User u:list) {
			System.out.println("@@@@@@@@@@@@@"+u.getUsername());
		}
		
		return "login";
	}

	@RequestMapping(value = "/book/addBook")
	public String addBook() {
		return "book/add";
	}


	@RequestMapping(value = "/staticPage/{name}")
	public String staticPage(@PathVariable String name) {
		return name;
	}
	
	@GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        System.out.println(serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say UserService");
        return "login";
    }
	@RequestMapping(value = "/demo/getOne")
	public String getOne(Model model) {
		
		model.addAttribute("message","getOne");
		return "/demo/1";
	}
	@RequestMapping(value = "/demo/getTwo")
	public String getTwo(Model model) {
		model.addAttribute("message","getTwo");
		return "/demo/2";
	}
	@RequestMapping(value = "/demo/getThree")
	public String getThree(Model model) {
		model.addAttribute("message","getThree");
		return "/demo/3";
	}
	@RequestMapping(value = "/demo/getFour")
	public String getFour(Model model) {
		model.addAttribute("message","getFour");
		return "/demo/4";
	}
}
