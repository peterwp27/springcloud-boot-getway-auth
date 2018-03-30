package cn.nriet.manage.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cn.liandi.framework.core.BasicService;
import cn.nriet.entity.User;

@Service
public class ITestService extends BasicService<User> {
	
	@SuppressWarnings("unchecked")
	public List<User> getForList(String apiURL, Map<String, Object> parameterMap){
		ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};  
		ResponseEntity<List<User>> responseEntity = restTemplate.exchange(apiURL, HttpMethod.GET, getHttpEntity(parameterMap), typeRef);  
		List<User> myModelClasses = responseEntity.getBody();  
        return myModelClasses;
    }
	
	
}
