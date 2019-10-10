package com.cif.utils.httpclient;

import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Oauth {
    public static OAuth2AccessToken getToken() {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri("http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials");
        resource.setClientId("cif-rest-service");
        resource.setClientSecret("cif-rest-service");
        resource.setGrantType("client_credentials");

        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static OAuth2AccessToken getToken(String url) {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri(url);
//		resource.setClientId("adp-interface");
//		resource.setClientSecret("adp-interface");
        resource.setClientId("cif-utc-rest");
        resource.setClientSecret("cif-utc-rest");
        resource.setGrantType("client_credentials");
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static OAuth2AccessToken getTokenPoutc() {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri("http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials");
        resource.setClientId("cif-utc-rest");
        resource.setClientSecret("cif-utc-rest");
        resource.setGrantType("client_credentials");

        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static OAuth2AccessToken getTokenLine() {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri("https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials");
        resource.setClientId("cif-rest-service");
        resource.setClientSecret("9q56z7V9aeE83D3f");
        resource.setGrantType("client_credentials");
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static OAuth2AccessToken getTokenLine(String url) {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri(url);
        resource.setClientId("cif-utc-rest");
        resource.setClientSecret("oE5lINlBkUvNRr2h0Ek3");
        resource.setGrantType("client_credentials");
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }


    // 通过反射动态获取执行方法
    public Object reflectionMethod(String methodName, String param) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = getClass().getMethod(methodName, String.class);
        return method.invoke(this, param);
    }

    // 获取发方法名
    public static String getMethod(String url) {
        if (url.contains("api.finupgroup.com")) {
            return "getTokenLine";
        } else return "getToken";
    }

    public static void main(String[] args) {
        System.out.println(getTokenPoutc().getValue());
    }

}
