package cn.nriet.entity;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Value;

public class SecurityEntity {
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccessTokenUri() {
		return accessTokenUri;
	}

	public void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	

}
