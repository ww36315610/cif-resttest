package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Maps;

public class TagQueryController_8000 implements Runnable {
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	String httpUrlMultiIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	String httpUrlOneIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	String httpUrlOneTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	String httpUrlMultiTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	String httpUrlTagType = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	String httpUrlTagWithCurrency = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagWithCurrency";

	// String httpUrlMultiIdCardNum =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/multiIdCardNum";
	// String httpUrlOneIdCardNum =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/oneIdCardNum";
	// String httpUrlOneTagName =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/oneTagName";
	// String httpUrlMultiTagName =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/multiTagName";
	// String httpUrlTagType =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/tagType";
	// String httpUrlTagWithCurrency =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/tagWithCurrency";

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
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}

	public void testOneTagName() {
		System.out.println(rd.getJsonArray(httpUrlOneTagName, map, getTagsRequest("httpUrlOneTagName")));
	}

	public String getTagsRequest(String url) {
		String json = null;
		Map<String, String> map = Maps.newHashMap();
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("8000");
		if (url.equals("httpUrlTagType")) {
			map.put("tagType", "jiea");
			map.put("idCardNum", "110111198909191416");
			map.put("requestId", "35");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(json);
		} else if (url.equals("httpUrlOneTagName")) {
			map.put("idCardNum", "110111198909191416");
			map.put("requestId", "35");
			map.put("tagName", "dx_request");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(json);
		}
		return json;
	}

	static TagQueryController_8000 pc = new TagQueryController_8000();

	public static void main(String[] args) {
		pc.testTagType();
		pc.testOneTagName();
	}

	@Override
	public void run() {
		pc.testTagType();
	}
}
