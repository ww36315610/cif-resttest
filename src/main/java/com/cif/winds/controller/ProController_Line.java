package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class ProController_Line {

	// 生产环境
	private static final String httpUrl = "https://api.puhuifinance.com/cif-utc-rest/api/v1/submitTask";
	private static final String asyncTagResultURL = "https://api.puhuifinance.com/cif-utc-rest/api/v1/asyncTagResult";
	private static final String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
	TagsRequest tr = new TagsRequest();
	static Map<String, Object> map = new HashMap<String, Object>();
	static OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}

	public String method() {
		RestfulDao rd = new RestfullDaoImp();
		return rd.getJsonArray(httpUrl, map, getTagsRequest()).getJSONObject(0).get("resultId").toString();
	}

	public String getTagsRequest() {
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("200");
		// req.setSync(false);
		req.setTagIds("2001");
		req.addParam("idCardNum", "xyd5267e9991a15002817bed9d7dde91aed776d9cadfff9bf5b83dfdbc6716201320160926");

		String json = JSONObject.toJSONString(req);
		System.out.println(">>>>>" + json);
		return json;
	}



	public static void main(String[] args) {
		ProController_Line pc = new ProController_Line();
		String resultId = null;
		try {
			//resultId = pc.method();
			pc.asyncTagResult("");
			//System.out.println(resultId);
		} catch (Exception e) {
			resultId = "resultId";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pc.asyncTagResult(resultId);
	}

	public void asyncTagResult(String resultId) {
		RestfulDao rd = new RestfullDaoImp();
		TagsRequest req = new TagsRequest();
		req.setChannelId("200");
		req.setTagIds("7001,7002,7003");
		req.addParam("resultId", "93686808e8d944b2bf24120e528cba86");
		// req.addParam("resultId", "05f209254d1c45f3b7767469ed6fb795");
		String json = JSON.toJSONString(req);
		System.out.println(rd.getJsonArray(asyncTagResultURL, map, json));
	}
}
