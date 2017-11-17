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

public class UAT_TagQueryController_8000 implements Runnable {
	String httpUrlTagType = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	String httpUrlOneIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getToken(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		System.out.println(token.getTokenType());
		System.out.println(token.getValue());
		map.put("Content-Type", "application/json");
		System.out.println(map);
	}

	public void testTagType() {
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}

	public void testOneTagName() {
		System.out.println(rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneTagName")));
	}

	public String getTagsRequest(String url) {
		String json = null;
		Map<String, String> map = Maps.newHashMap();
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("8000");
		if (url.equals("httpUrlTagType")) {
			map.put("tagType", "jiea");
			map.put("idCardNum", "372922198408053712");
			map.put("requestId", "104");
			// map.put("userId", "34");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(json);
		}
//		 json =
//		 "{\"channelId\":\"8000\",\"params\":{\"requestId\":\"50\",\"tagName\":\"dx_request\",\"idCardNum\":\"110119198903031111\"},\"sync\":false}";
		// json =
		// "{\"channelId\":\"8000\",\"params\":{"idCardNum\":\"110119198903031111\"\"tagName\":\"testb\"},\"sync\":false}";

		return json;
	}

	static UAT_TagQueryController_8000 pc = new UAT_TagQueryController_8000();

	public static void main(String[] args) {
		 pc.testTagType();
//		pc.testOneTagName();
		// else if (url.equals("httpUrlOneTagName")) {
		// map.put("tagName", "FL_LR_id'");
		// map.put("requestId", "882441");
		// req.setParams(map);
		// json = JSONObject.toJSONString(req);
		// String json =
		// "{\"channelId\":\"8000\",\"params\":{\"requestId\":\"50\",\"tagName\":\"dx_request\",\"idCardNum\":\"110119198903031111\"},\"sync\":false}";
		// pc.testTagType();
	}

	@Override
	public void run() {
		pc.testTagType();
	}
}
