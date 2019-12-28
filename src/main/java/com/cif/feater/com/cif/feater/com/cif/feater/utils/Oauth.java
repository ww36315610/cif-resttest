package com.cif.feater.com.cif.feater.com.cif.feater.utils;

import com.cif.utils.file.ConfigTools;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class Oauth extends ConfigTools {
    static  ResourceOwnerPasswordAccessTokenProvider provider;
    static  ResourceOwnerPasswordResourceDetails resource;

    static {
        provider = new ResourceOwnerPasswordAccessTokenProvider();
        resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
     }

    //返回OAuth2AccessToken
    public static OAuth2AccessToken getToken(String channel) {
        resource.setAccessTokenUri(config.getString(channel+"."+"oauth.url"));
        resource.setClientId(config.getString(channel+"."+"oauth.clientId"));
        resource.setClientSecret(config.getString(channel+"."+"oauth.cientSecret"));
        resource.setGrantType(config.getString(channel +"."+"oauth.grantType"));

        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static void main(String[] args) {
        OAuth2AccessToken ooo = getToken("oauth_test_server");
        System.out.println(ooo.getTokenType()+"****"+ooo.getValue());
    }
}

