package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class TagQueryController_7000 {
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	String httpUrlTagType = "https://api.puhuifinance.com/cif-utc-rest/api/v1/asyncTagResult";

	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		System.out.println(map);
	}

	public void testTagType() {
		String json1 = "{\"channelId\":\"7000\",\"params\":{\"day\":\"2017-11-13\"}";
		System.out.println(rd.getJsonArray(httpUrlTagType, map, json1));
	}

	static TagQueryController_7000 pc = new TagQueryController_7000();

	public static void main(String[] args) {
		pc.testTagType();
	}
}
