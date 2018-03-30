/**  
 * Project Name:WH-OverseasChannel-Service  
 * File Name:AbstractBaseGet.java  
 * Package Name:com.bestwehotel.overseaschannel.service.ws.core  
 * Date:2017年10月24日下午4:21:26  
 * Copyright (c) 2017, hubs1.net All Rights Reserved.  
 *  
 */  
  
package cn.nriet.manage.util;  

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
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

import com.google.gson.annotations.SerializedName;

  
/**  
 * ClassName: AbstractBaseGet <br/>   
 * date: 2017年10月24日 下午4:21:26 <br/>  
 *
 * @Description 抽象请求接口类
 * @author Yawn.Chen@BestWeHotel.com  
 * @version   
 * @since JDK 1.8  
 */
public abstract class AbstractBaseGet<RQ, RS> {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseGet.class);

    @Autowired
    private RestTemplate restTemplate;

    
    /**
     * get: RestTemplate.get拉取
     *  
     * @author Yawn.Chen@BestWeHotel.com  
     * @param apiURL
     * @param rq (仅支持map)
     * @param rsClass
     * @return  
     * @since JDK 1.8
     */
    @SuppressWarnings("unchecked")
    protected RS get(String apiURL, RQ rq, Class<? extends RS> rsClass){
        RS response = null;
        HttpEntity<RQ> httpEntity = null;
        try {
            if(null != rq){
                apiURL = parseUrlVariables(apiURL, rq);
            }
            httpEntity = getHttpEntity(rq);
            
            /*String jsonResponse = restTemplate.getForObject(apiURL, String.class, rq);
            if(!StringUtils.isEmpty(jsonResponse)){
                response = (RS) GsonUtil.parseJson(jsonResponse, rsClass);
            }
            
            System.out.println(GsonUtil.toJsonString(response));*/
            
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<String>() {});
            if(null != responseEntity && !StringUtils.isEmpty(responseEntity.getBody())){
                response = (RS)responseEntity.getBody();
            }
            logger.debug("AbstractBaseGet.post({}, {}, {}) result:{}", apiURL, rq, rsClass, response);
        } catch (Exception e) {
            logger.error("AbstractBaseGet.post({}, {}, {}) result:{} error:{}", apiURL, rq, rsClass, response, e);
        }
        return response;
    }
    /**
     * post: RestTemplate.post拉取
     *  
     * @author Yawn.Chen@BestWeHotel.com  
     * @param apiURL
     * @return  
     * @since JDK 1.8
     */
    protected RS post(String apiURL, RQ rq, Class<? extends RS> rsClass){
        RS response = null;
        HttpEntity<RQ> httpEntity = null;
        try {
            httpEntity = getHttpEntity(rq);
            
            response = restTemplate.postForObject(apiURL, httpEntity, rsClass);
            logger.debug("AbstractBaseGet.post({}, {}, {}) result:{}", apiURL, rq, rsClass, response);
        } catch (Exception e) {
            logger.error("AbstractBaseGet.post({}, {}, {}) result:{} error:{}", apiURL, rq, rsClass, response, e);
        }
        return response;
    }


    
    /**
     * getHttpEntity: 统一请求头
     *  
     * @author Yawn.Chen@BestWeHotel.com  
     * @param rq
     * @return  
     * @since JDK 1.8
     */
    protected HttpEntity<RQ> getHttpEntity(RQ rq) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer 14a2ef29-82c9-4454-9e97-eeb61798f11e");
        headers.add("Accept-Charset", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return  new HttpEntity<RQ>(rq, headers);
    }
    
    //处理参数
    protected String parseUrlVariables(String apiURL, RQ rq){
        if(!(rq instanceof Object)) return apiURL;
        boolean isExistsParams = -1 < apiURL.indexOf("?");
        if(!isExistsParams){
            apiURL = apiURL +"?";
        }else if(!apiURL.endsWith("&")){
            apiURL = apiURL +"&";
        }
        if(rq instanceof Map){
           @SuppressWarnings("rawtypes")
		   Map params = (HashMap)rq;
           @SuppressWarnings("unchecked")
		   Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
           while (it.hasNext()) {
               Map.Entry<String, String> entry = it.next();
               apiURL = apiURL + entry.getKey() + "=" +entry.getValue();
               if(it.hasNext()){
            	   apiURL = apiURL + "&";
               }
           }
           return apiURL;	
        }
        Class<? extends Object> rqClass = rq.getClass();
        //父类
        Class<? extends Object> baseRqClass = rqClass.getSuperclass();
        Field[] baseFields = baseRqClass.getDeclaredFields();
        for(Field f : baseFields){
            f.setAccessible(true);
            String name = f.getName();
            if (name.indexOf("serialVersion") > -1){
                continue;
            }
            try {
                String fl = name.substring(0, 1).toUpperCase();
                String getMethodName = "get" + fl + name.substring(1);
                Annotation[] as = f.getAnnotations();
                for(Annotation a : as){
                    if(a instanceof SerializedName){
                        SerializedName sn = (SerializedName)a;
                        name = sn.value();
                    }
                }
                Method getMethod = rqClass.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(rq, new Object[] {});
                if (null != value) {
                    apiURL = apiURL + name + "=" + value + "&";
                }
            } catch (Exception e) {
                logger.error("Object to Map error:", e);
            }
        }
        //自己class
        Field[] fields = rqClass.getDeclaredFields();
        for(Field f : fields){
            String name = f.getName();
            if (name.indexOf("serialVersion") > -1){
                continue;
            }
            try {
                String fl = name.substring(0, 1).toUpperCase();
                String getMethodName = "get" + fl + name.substring(1);
                Annotation[] as = f.getAnnotations();
                for(Annotation a : as){
                    if(a instanceof SerializedName){
                        SerializedName sn = (SerializedName)a;
                        name = sn.value();
                    }
                }
                Method getMethod = rqClass.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(rq, new Object[] {});
                if (null != value) {
                    apiURL = apiURL + name + "=" + value + "&";
                }
            } catch (Exception e) {
                logger.error("Object to Map error:", e);
            }
        }        
        return apiURL.substring(0, apiURL.length()-1);
    }
}
  
