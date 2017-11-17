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

public class Uat_HttpResponse {
	static HttpClientImp httpCL = new HttpClientImp();
	static HttpClient client = new DefaultHttpClient();
	// UAT_Oauth
	static OAuth2AccessToken token = Oauth.getToken();

	public static JSONArray getResponse(JSONObject jsonObjects, String change) {
		// UATresfull接口地址
		String before = "http://t1.zuul.pub.puhuifinance.com/cif-rest-server/";

		String url = before + jsonObjects.getString("url");
		String params = jsonObjects.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
//		System.out.println(jsonArrayHttp);
		return jsonArrayHttp;
	}

	public static Map<String, Object> getAuthMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}
	
	public static void main(String[] args) {
//		String url = "http://d1.zuul.pub.puhuifinance.com:8765/cif-rest-server/api/v1/getOneBasicColumn";
		String url = "http://t1.zuul.pub.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		String json = "{\"idCardNum\":\"xy5d7fd593582e54cdbeaf1b6b02072f471e76b3ef271947443a6992c6d372723620160926\",\"type\":\"mobile_info.voices\"}";
		json = "{\"idCardNum\":\"xy5b7838480946aaaaf0e7b0825ac9e59a20160926\",\"type\":\"taobao_info.taobao_basic\"}";
		
		url ="http://t1.zuul.pub.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		json = "{\"idCardNum\":\"xy95b2e6be3cc58106c162e8adf9029c4920160926\",\"type\":\"mobile_info.voices\"}";
		
		System.out.println(httpCL.postHttp(client, url, getAuthMap(), json));
	}
}
