package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class ProController {

	// test环境
	private static final String httpUrl = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/submitTask";
	private static final String asyncTagResultURL = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/asyncTagResult";
	// dev环境
	// private static final String httpUrl =
	// "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/submitTask";
	// private static final String asyncTagResultURL =
	// "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/asyncTagResult";
	private static final String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
	TagsRequest tr = new TagsRequest();
	static Map<String, Object> map = new HashMap<String, Object>();
	static OAuth2AccessToken token = Oauth.getToken(autoUrl);
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		System.out.println(map);
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
		// req.addParam("applyNo", "102201611074135590A");
		req.addParam("idCardNum", "xy2c0d4966951aec732159ce203618863c20160926");
		// req.addParam("idCardNum",
		// "xy741e7713f9064ecab5318061d0d67e2ce98ca90df918acc1733430cae17906ac20160926");
		// req.addParam("idCardNum",
		// "xy0ee1e4e93717543ceabce8169f54b4489ab790f1bf65b9a4b2edeba6fc55fbf620160926");
		// req.addParam("idCardNum",
		// "xyfd96767abf75adba43ada94d02549b06cb46b9eea541517731f32354dbd4bbe120160926");
		// req.addParam("idCardNum",
		// "xy183cc70d1a30d0035799f522dc29c7dbbaa79b92ee8f1f0209af66046531698720160926");
		// req.addParam("idCardNum",
		// "xy741e7713f9064ecab5318061d0d67e2ce98ca90df918acc1733430cae17906ac20160926");
		// req.addParam("idCardNum",
		// "xyb76877f17d1588e080e4f2aa88d3c0361a40060626c877594a8fabe583b29bdf20160926");
		// req.addParam("idCardNum",
		// "xyece11eb7c3753e1ce6ce444f4b7c676a49fee907273ab7f791f205005cf52adf20160926");
		// req.addParam("idCardNum",
		// "xy85a34f1989cf0c3ad18543481eb957393536ed4c6ded295595ee029c743b3da520160926");

		String json = JSONObject.toJSONString(req);
		System.out.println(">>>>>" + json);
		return json;
	}

	public static void main(String[] args) {
		ProController pc = new ProController();
		String resultId = null;
		try {
			//resultId = pc.method();
			pc.asyncTagResult("");
			System.out.println(resultId);
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
		req.setTagIds("2001");
		req.addParam("resultId", "93686808e8d944b2bf24120e528cba86");
		// req.addParam("resultId", "05f209254d1c45f3b7767469ed6fb795");
		String json = JSON.toJSONString(req);
		System.out.println(rd.getJsonArray(asyncTagResultURL, map, json));
	}
}
