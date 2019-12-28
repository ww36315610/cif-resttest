package com.cif.now.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cif.now.utils.resource.HttpClientTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;

public class ProController {
	private static final String SUBMITTASKURL = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/submitTask";
	private static final String ASNYNCTAGURL = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/asyncTagResult";
	private static final String OAUTHURL = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
	TagsRequest tr = new TagsRequest();
	static Map<String, Object> map = new HashMap<String, Object>();
	static OAuth2AccessToken token = Oauth.getToken(OAUTHURL);
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}

	public static void main(String[] args) {
		HttpClientTools hct = new HttpClientTools();
		String params = "{\"channelId\":\"200\",\"delayCount\":0,\"needDebugInfo\":true,\"params\":{\"idCardNum\":\"xy2c0d4966951aec732159ce203618863c20160926\"},\"singleParamKey\":\"idCardNum\",\"singleParamValue\":\"xy2c0d4966951aec732159ce203618863c20160926\",\"sync\":false,\"tagIds\":\"2001\"}";
		System.out.println(hct.postHttp(SUBMITTASKURL, map, params));
	}
}
