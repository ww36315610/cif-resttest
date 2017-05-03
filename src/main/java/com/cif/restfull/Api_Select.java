package com.cif.restfull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.ConfigTools;
import com.cif.utils.httpclient.HttpClientImp;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.mongo.MongoStoreEnum.QianZhan;

public class Api_Select extends ConfigTools {
	HttpClientImp httpCL = new HttpClientImp();
	HttpClient client = new DefaultHttpClient();
	Api_Mongo apiMongo = new Api_Mongo();

	static OAuth2AccessToken token = Oauth.getToken();
	static JSONArray jsonArrayHttpClient;
	static JSONArray jsonArrayMongo;

	public static void main(String[] args) {
	}

	@Test
	public static void testMethod() {
		Api_Mongo apiMongo = new Api_Mongo();
		Api_Select apiSelect = new Api_Select();
		jsonArrayMongo = apiMongo.doMongo("mongo_qz", QianZhan.class);
		System.out.println(jsonArrayMongo.get(0));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 1; i < 24; i++) {
			jsonArrayHttpClient = apiSelect.getResponse(i);
			if (jsonArrayHttpClient.size() == 1) {
				JSONObject jsonHttpClient = JSONObject.parseObject(jsonArrayHttpClient.get(0).toString());
				System.out.println("size=>>>>>>>>>>>>>>>>" + jsonHttpClient.getJSONArray("tag"));
				Assert.assertEquals(jsonHttpClient.get("resultCode"), "SUCCESS", "返回码不正确！！！！！");
				Assert.assertTrue(jsonHttpClient.getJSONArray("tag").size() >= 1);
			} else {
				System.out.println("JSONArray异常！！！");
			}
		}
	}

	public JSONArray getResponse(int num) {
		String url = config.getString("restapi_url.url1");
		String params = config.getString("restapi_qz.case_" + num);
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), params);
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
