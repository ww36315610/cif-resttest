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
	String httpUrlTagType = "http://cif-utc-rest.cif.beta/api/v1/tagType";
//	String httpUrlOneIdCardNum = "http://cif-utc-rest.cif.beta/api/v1/oneTagName";
//	String httpUrlOneIdCardNum = "http://10.10.8.88:30333/api/v1/oneTagName";
String httpUrlOneIdCardNum = "http://10.10.8.88:30333/trace";
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
		req.setChannelId("3005");
		if (url.equals("httpUrlOneTagName")) {
//			map.put("id_no", "51132419841109107X");
//			map.put("id_no","555");
		    map.put("id_no", "xyf151ca1a812feb546055db1fa981418820160926");
			map.put("tagName", "FPBAF016");
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
//		 pc.testTagType();
		pc.testOneTagName();
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
