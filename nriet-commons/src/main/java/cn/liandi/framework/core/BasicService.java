package cn.liandi.framework.core;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.liandi.framework.util.AuthorizationManager;
import cn.nriet.entity.User;

/**
 * for RestAPi
 * @author b_wangpei
 *
 */
public abstract class BasicService<T> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	final String GET_STRING_BIND = "get_string_bind";
	final String POST_STRING_BIND = "post_string_bind";

	
	@Autowired
	private AuthorizationManager authorizationManager;
	@Autowired  
	protected RestTemplate restTemplate; 
	
	/**
	 * open API for developer
	 * @param URL
	 * 				restAPI地址
	 * @param parameterMap
	 * 						请求参数
	 * @return String
	 * 					返回原始的ResponseEntity字符串
	 */
	public String getResponseString(String URL,Map<String, Object> parameterMap) {
		return Routing(URL,GET_STRING_BIND,parameterMap).getBody().toString();
	}
	
	/**
	 * 返回指定实体类的对象
	 * @param apiURL   
	 * 					restAPI地址
	 * @param rsClass
	 * 					返回实体类型
	 * @param parameterMap
	 * 						请求参数
	 * @return
	 * 			T
	 */
	@SuppressWarnings("unchecked")
	public T getForObject(String apiURL, Class<? extends T> rsClass, Map<String, Object> parameterMap){
        ResponseEntity<String> responseEntity = Routing(apiURL,GET_STRING_BIND,parameterMap);
        return Response(responseEntity,rsClass);
    }
	
	
    /**
     * post: RestTemplate.post拉取
     *
     * @param apiURL
     * 					restAPI地址
     * @param rsClass
     * 					返回实体类型
     * @param parameterMap
     * 						参数传递
     * @return  
     * 			T
     */
    public T postForEntity(String apiURL,  Class<? extends T> rsClass,Map<String, Object> parameterMap){
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiURL, getHttpEntity(parameterMap) , String.class );
        return Response(responseEntity,rsClass);
    }
	
	/**
	 *----------------------------------------------------------------------------------------------------------- 
	 * routing for set acc_tocken 
	 * @param uri
	 * @param router
	 * @return
	 */
	private ResponseEntity<String> Routing (String uri,String router,Map<String, Object> parameterMap) {
		switch (router)
		{
		    case GET_STRING_BIND:
		    	return doExcute(uri,HttpMethod.GET,getHttpEntity(),parameterMap);
//		    	break;
		    case POST_STRING_BIND:
		    	return doExcute(uri,HttpMethod.POST,getHttpEntity(),parameterMap);
//		    	break;
		}
		return null;
	}
	/**
	 * 
	 * @param responseEntity
	 * @param rsClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T Response(ResponseEntity<String> responseEntity,Class<? extends T> rsClass) {
		T response = null;
        try {
        	if(null != responseEntity && !StringUtils.isEmpty(responseEntity.getBody())){
            	response = (T)JSON.parseObject(responseEntity.getBody().toString(), rsClass);
            }
        } catch (Exception e) {
        }
        return response;
	}
	/**
	 * exchange总的执行方法
	 * @param uri
	 * @param hm
	 * @param httpEntity
	 * 				封装数据参数header
	 * @return
	 */
	private ResponseEntity<String> doExcute(String uri,HttpMethod hm,HttpEntity httpEntity,Map<String, Object> parameterMap) {
		return restTemplate.exchange(uri, hm, httpEntity, new ParameterizedTypeReference<String>() {},parameterMap);
	}
	
	/**
     * getHttpEntity: 统一请求头
     *   
     * @param 
     * @return  
     * @since 
     */
	protected HttpEntity getHttpEntity() {
        return  new HttpEntity(getHttpHeaders());
    }
	protected HttpEntity getHttpEntity(Map<String, Object> parameterMap) {
    	ObjectMapper mapper = new ObjectMapper();
        String value = "";
		try {
			value = mapper.writeValueAsString(parameterMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return  new HttpEntity(value,getHttpHeaders());
    }
    private HttpHeaders getHttpHeaders() {
    	HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + authorizationManager.ACC_TOCKEN);
        headers.add("Accept-Charset", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }
}
