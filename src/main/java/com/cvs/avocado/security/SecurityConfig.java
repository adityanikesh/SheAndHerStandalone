package com.cvs.avocado.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationFacade authenticationFacade;

	@Bean
	public PasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
//	@Bean
//	@Autowired
	public LogoutHandler logoutHandler() {
		System.out.println("Handler called");
//		OAuth2AccessToken accessToken = this.tokenStore().getAccessToken(this.authenticationFacade.getOAuth2Authentication());
//		tokenStore.removeAccessToken(accessToken);
//		tokenStore.removeRefreshToken(accessToken.getRefreshToken());
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		return logoutHandler;
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(userPasswordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().ignoringAntMatchers("/logout/**")
		.and()
		.authorizeRequests()
		.antMatchers("/index.html", "/**.js", "/**.css", "/").permitAll()
		.anyRequest().authenticated()
		.and()
		.logout().permitAll()
		.and()
		.httpBasic();
	}

}
