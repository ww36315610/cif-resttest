package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class UAT_TagQueryControllerMysql {
	
	
	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	static Map<String, Object> map = new HashMap<String, Object>();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getToken(autoUrl);
		System.out.println(token.getValue());
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}
	int i = 1;

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseDateMysql.class)
	public void method(JSONObject object) {

		RestfulDao rd = new RestfullDaoImp();
		JSONArray jsonArray = rd.getJsonArray(object.get("url").toString(), map, object.get("params").toString());

		JSONObject json = jsonArray.getJSONObject(0);
		String resultMap = json.getString("resultMap");
		resultMap = resultMap.replace("\"", "\\\"");
		String sql = "UPDATE tagQuery SET result= \"" + resultMap + "\" where id =" + i;
		CaseDateMysql.insertResult(sql);
		i = i + 1;
		// System.out.println(resultMap);
	}
	
	
}
