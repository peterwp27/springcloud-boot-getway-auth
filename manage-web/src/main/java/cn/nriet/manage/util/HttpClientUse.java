package cn.nriet.manage.util;

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler; 
import org.apache.commons.httpclient.Header; 
import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpException; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.UsernamePasswordCredentials; 
import org.apache.commons.httpclient.auth.AuthScope; 
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON; 

public class HttpClientUse {
	
	public static String getAcctoken() {
		String access_token = "";
		// TODO Auto-generated method stub
				HttpClient httpClient = new HttpClient(); 
				//需要验证 
				UsernamePasswordCredentials creds = new UsernamePasswordCredentials("client", "secret");

				httpClient.getState().setCredentials(AuthScope.ANY, creds); 

				//设置http头 
				List <Header> headers = new ArrayList <Header>(); 
				headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")); 
				httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers); 

//				GetMethod method = new GetMethod("http://localhost:8060/uaa/oauth/token?password=password&username=anil&grant_type=password&scope=read%20write");
				PostMethod method = new PostMethod("http://localhost:8060/uaa/oauth/token?password=password&username=anil&grant_type=password&scope=read%20write");
				method.setDoAuthentication(true);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				new DefaultHttpMethodRetryHandler(3, false)); 
				try { 
				int statusCode = httpClient.executeMethod(method); 
				if (statusCode != HttpStatus.SC_OK) { 
					
				System.out.println("Method failed code="+statusCode+": " + method.getStatusLine()); 

				} else { 
					String acc_token = new String(method.getResponseBody(), "utf-8");
					Map<String,Object> map = JSON.parseObject(acc_token, Map.class);
					System.out.println("########################tocken"+map.get("access_token"));
					access_token = map.get("access_token").toString();
//				System.out.println(new String(method.getResponseBody(), "utf-8")); 
				} 
				}catch (Exception e) {e.printStackTrace();}
				finally { 
				method.releaseConnection(); 
				}
				return access_token;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient(); 
		//需要验证 
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials("client", "secret");

		httpClient.getState().setCredentials(AuthScope.ANY, creds); 


		//设置http头 
		List <Header> headers = new ArrayList <Header>(); 
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")); 
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers); 

//		GetMethod method = new GetMethod("http://localhost:8060/uaa/oauth/token?password=password&username=anil&grant_type=password&scope=read%20write");
		PostMethod method = new PostMethod("http://localhost:8060/uaa/oauth/token?password=password&username=anil&grant_type=password&scope=read%20write");
		method.setDoAuthentication(true);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
		new DefaultHttpMethodRetryHandler(3, false)); 
		try { 
		int statusCode = httpClient.executeMethod(method); 
		if (statusCode != HttpStatus.SC_OK) { 
			
		System.out.println("Method failed code="+statusCode+": " + method.getStatusLine()); 

		} else { 
			String acc_token = new String(method.getResponseBody(), "utf-8");
			Map<String,Object> map = JSON.parseObject(acc_token, Map.class);
			System.out.println(map.get("access_token"));
		System.out.println(new String(method.getResponseBody(), "utf-8")); 
		} 
		}catch (Exception e) {e.printStackTrace();}
		finally { 
		method.releaseConnection(); 
		} 
	}

}
