package cn.liandi.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

import cn.nriet.entity.SecurityEntity;

/**
 * 登录授权管理 Created by wangpei.
 */
public class AuthorizationManager implements InitializingBean {
	private Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);
	public static String ACC_TOCKEN = "";
	private SecurityEntity security;

	public AuthorizationManager(SecurityEntity security1) {
		security = security1;
//		System.out.println("获取到的security##############token_url：：：" + security.getAccessTokenUri() + ";" + security.getClientId() + ";" + security.getClientSecret() +";"+ security.getGrant_type() + ";"+security.getName()+";"+ security.getPassword()+";"+security.getScope());
	}

	@Override
	@Scheduled(cron = "0 2/58 * * * ?")
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		this.ACC_TOCKEN = this.getAccessTocken();
		System.out.println(ACC_TOCKEN);
	}

	public String getAccessTocken() {
		String access_token = "";
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient();
		// 需要验证
//		UsernamePasswordCredentials creds = new UsernamePasswordCredentials("client", "secret");
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(security.getClientId(), security.getClientSecret());
		httpClient.getState().setCredentials(AuthScope.ANY, creds);
		// 设置http头
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		String uri = security.getAccessTokenUri() + "?password="+ security.getPassword() +"&username=" + security.getName() +"&grant_type=" + security.getGrant_type() + "&scope=" + security.getScope();
//		System.out.println("最终访问的url::::" + uri);
//		 PostMethod method = new PostMethod(
//		 "http://localhost:8060/uaa/oauth/token?password=password&username=anil&grant_type=password&scope=read%20write");
		PostMethod method = new PostMethod(uri);
		method.setDoAuthentication(true);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {

				System.out.println("Method failed code=" + statusCode + ": " + method.getStatusLine());

			} else {
				String acc_token = new String(method.getResponseBody(), "utf-8");
				Map<String, Object> map = JSON.parseObject(acc_token, Map.class);
//				System.out.println("########################tocken" + map.get("access_token"));
				access_token = map.get("access_token").toString();
				// System.out.println(new String(method.getResponseBody(), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return access_token;
	}

}
