package cn.liandi.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cn.liandi.framework.util.AuthorizationManager;

public abstract class BasicController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	
}
