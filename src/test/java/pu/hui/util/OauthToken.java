package pu.hui.util;

import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class OauthToken {
	public static OAuth2AccessToken getToken() {
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientAuthenticationScheme(AuthenticationScheme.form);
		resource.setAccessTokenUri("http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials");
		resource.setClientId("cif-rest-service");
		resource.setClientSecret("cif-rest-service");
		resource.setGrantType("client_credentials");

		OAuth2AccessToken accessToken = provider.obtainAccessToken(resource,
				new DefaultAccessTokenRequest());
		return accessToken;
	}

	public static void main(String[] args) {
		System.out.println(getToken().getValue());
		System.out.println(getToken().getTokenType());
	}
}
