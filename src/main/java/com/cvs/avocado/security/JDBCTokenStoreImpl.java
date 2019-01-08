package com.cvs.avocado.security;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

public class JDBCTokenStoreImpl extends JdbcTokenStore{
	
	@Autowired
	HttpServletFacade httpServlet;
	
	private final JdbcTemplate jdbcTemplate;
	
	private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


	public JDBCTokenStoreImpl(DataSource dataSource) {
		super(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}
	
	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		System.out.println("Management IP: " + this.httpServlet.getRemoteAddress());
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		
		if (readAccessToken(token.getValue())!=null) {
			removeAccessToken(token.getValue());
		}

		jdbcTemplate.update(DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT, new Object[] { extractTokenKey(token.getValue()),
				new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
				authentication.isClientOnly() ? null : authentication.getName(),
				authentication.getOAuth2Request().getClientId(),
				new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] {
				Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
	}

	
}
