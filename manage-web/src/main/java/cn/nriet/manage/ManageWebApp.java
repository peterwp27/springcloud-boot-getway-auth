package cn.nriet.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

import cn.liandi.framework.core.BasicApplication;

@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableOAuth2Client
@EntityScan("cn.nriet.entity") 
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@ComponentScan(basePackages = { "cn.liandi.framework.core" })  
public class ManageWebApp extends BasicApplication{
	@Autowired  
    private RestTemplateBuilder builder;  
	
        // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例  
    @Bean  
    public RestTemplate restTemplate() {
        return builder.build();
    }
    
	public static void main(String[] args) {
		new SpringApplicationBuilder(ManageWebApp.class).run(args);
	}

}
