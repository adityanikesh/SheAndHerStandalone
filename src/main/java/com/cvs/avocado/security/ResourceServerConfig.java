package com.cvs.avocado.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "appmanager";
    
    @Autowired
    TokenStore tokenStore;
    
	@Bean
	public CookieCsrfTokenRepository csrfTokenRepository() {
		CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
		csrfTokenRepository.setCookieHttpOnly(false);
		csrfTokenRepository.setCookieName("Avocado-XSRF-Token");
		csrfTokenRepository.setHeaderName("Avocado-XSRF-Token");
		csrfTokenRepository.setCookiePath("/");
		return csrfTokenRepository;
	}
    
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
        .resourceId(RESOURCE_ID)
        .tokenStore(tokenStore);
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()//csrfTokenRepository(csrfTokenRepository())
//		.and()
		.anonymous()
		.and()
		.requestMatchers()
		.antMatchers("/api/**", "/adpl/**")
		.and()
        .authorizeRequests()
        .anyRequest().authenticated();       
	}
}
