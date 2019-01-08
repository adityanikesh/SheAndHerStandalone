package com.cvs.avocado.security;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
	
	public CustomJwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("tomcat-keystore.p12"), "changeit".toCharArray());
		this.setKeyPair(keyStoreKeyFactory.getKeyPair("tomcat"));
	}
}
