package cn.liandi.framework.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.liandi.framework.util.AuthorizationManager;
import cn.nriet.entity.SecurityEntity;

/**
 * app基础类，实现此类即可完成oauth认证服务，仅供内部boot鉴权
 * @author b_wangpei
 *
 */
@EnableScheduling
public abstract class BasicApplication {

	@Value("${security.user.name}")
	String name;
	@Value("${security.user.password}")
	String password;

	@Value("${security.oauth2.client.clientId}")
	String clientId;

	@Value("${security.oauth2.client.clientSecret}")
	String clientSecret;

	@Value("${security.oauth2.client.accessTokenUri}")
	String accessTokenUri;

	@Value("${security.oauth2.client.grant_type}")
	String grant_type;

	@Value("${security.oauth2.client.scope}")
	String scope;

	@Bean
	public SecurityEntity securityEntity() {
		SecurityEntity s = new SecurityEntity();
		s.setAccessTokenUri(accessTokenUri);
		s.setClientId(clientId);
		s.setClientSecret(clientSecret);
		s.setGrant_type(grant_type);
		s.setName(name);
		s.setPassword(password);
		s.setScope(scope);
		return s;
	}

	@Autowired
	private SecurityEntity security;
	@Autowired
	private AuthorizationManager authorizationManager;
	
	
	@Bean
	public AuthorizationManager authorizationManager() {
//		System.out.println("定时器");
		return new AuthorizationManager(security);
	}
}
