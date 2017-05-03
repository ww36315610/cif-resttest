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

public class HttpResponse {
	static HttpClientImp httpCL = new HttpClientImp();
	static HttpClient client = new DefaultHttpClient();
	static OAuth2AccessToken token = Oauth.getToken();

	public static JSONArray getResponse(JSONObject jsonObjects, String change) {
		String before = "http://t1.zuul.pub.puhuifinance.com/cif-rest-server/";
		String url = before + jsonObjects.getString("url");
		String params = jsonObjects.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
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
