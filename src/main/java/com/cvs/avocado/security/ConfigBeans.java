package com.cvs.avocado.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class ConfigBeans {

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		return new CustomJwtAccessTokenConverter();
	}
	
	@Bean
	public TokenStore tokenStore() {
//		return new JDBCTokenStoreImpl(dataSource);
//		 return new JwtTokenStore(jwtTokenEnhancer());
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public PasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
}
