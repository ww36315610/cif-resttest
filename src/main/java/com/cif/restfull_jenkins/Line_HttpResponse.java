package com.cif.restfull_jenkins;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.HttpClientImp;
import com.cif.utils.httpclient.Oauth;

public class Line_HttpResponse {
	static HttpClientImp httpCL = new HttpClientImp();
	static HttpClient client = new DefaultHttpClient();
	// 生产Oauth
	static OAuth2AccessToken token = Oauth.getTokenLine();

	public static JSONArray getResponse(JSONObject jsonObjects, String change) {
		// 线上resfull接口地址
		String before = "https://api.puhuifinance.com/cif-rest-server-pre/";
		// 线上resfull接口地址 --预发布
		// String before ="https://api.puhuifinance.com/cif-rest-server-pre/";

		String url = before + jsonObjects.getString("url");
		String params = jsonObjects.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
		System.out.println(jsonArrayHttp);
		return jsonArrayHttp;
	}

	public static Map<String, Object> getAuthMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}
}
