package com.cif.restfull_jenkins;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.httpclient.HttpClientImp;
import com.cif.utils.httpclient.Oauth;

public class ResfullTest {
	HttpClientImp httpCL = new HttpClientImp();
	HttpClient client = new DefaultHttpClient();
	OAuth2AccessToken token = Oauth.getToken();
	String before = "http://t1.zuul.pub.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
//	String paramsJson = "{\"idCardNum\":\"xyc6445d51e90c99da8b3ee260adffff03fabbe652aab6a2640bc4f48df73ecdb620160926\",\"type\":\"credit_card_info.credit_basic\"}";
	
//	String paramsJson = "{\"idCardNum\":\"xyfde3f8726d9901eaccf19a65add0ceb2b0efeae4fd494eac7bc255f26df4ac3520160926\",\"type\":\"credit_card_info.credit_basic\"}";
	String paramsJson = "{\"idCardNum\":\"xyfde3f8726d9901eaccf19a65add0ceb2b0efeae4fd494eac7bc255f26df4ac3520160926\",\"type\":\"mobile_info.basic_info\"}";
	public JSONArray getResponse() {
		String url = before;
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), paramsJson);
		return jsonArrayHttp;
	}

	public Map<String, Object> getAuthMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}

	public static void main(String[] args) {
		ResfullTest rt = new ResfullTest();
		System.out.println(rt.getResponse());
	}
}
