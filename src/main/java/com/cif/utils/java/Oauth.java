package com.cif.utils.java;

import com.cif.utils.file.ConfigTools;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.HashMap;
import java.util.Map;

public class Oauth extends ConfigTools {
    static  Map<String, Object> map = new HashMap<String, Object>();
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

    //构造http消息头
    public static Map<String,Object> getHeader(String channel) {
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", getToken(channel).getTokenType(), getToken(channel).getValue()));
        map.put("Content-Type", "application/json;charset=UTF-8");
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getHeader("oauth_test_server"));

    }
}
