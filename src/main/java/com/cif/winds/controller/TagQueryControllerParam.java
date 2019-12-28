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

public class TagQueryControllerParam {
	String httpUrlMultiIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	String httpUrlOneIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	String httpUrlOneTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	String httpUrlMultiTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	String httpUrlTagType = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	String httpUrlTagWithCurrency = "https://api.puhuifinance.com/cif-utc-rest/api/v1/currencyTag";
	static Map<String, Object> map = new HashMap<String, Object>();
	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		System.out.println(token.getValue());
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}

	public void testMultiIdCardNum() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlMultiIdCardNum, map, getTagsRequest("httpUrlMultiIdCardNum")));
	}

	public void testOneIdCardNum() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneIdCardNum")));
	}

	public void testOneTagName() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlOneTagName, map, getTagsRequest("httpUrlOneTagName")));
	}

	public void testMultiTagName() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlMultiTagName, map, getTagsRequest("httpUrlMultiTagName")));
	}

	public void testTagType() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}

	public void testTagWithCurrency() {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlTagWithCurrency, map, getTagsRequest("httpUrlTagWithCurrency")));
	}

	public String getTagsRequest(String url) {
		String json = null;
		Map<String, String> map = Maps.newHashMap();
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("3000");
		if (url.equals("httpUrlMultiIdCardNum")) {
			map.put("param",
					"xy61e03790cc0e8ef23ff264b5b31fd8a83c5173ea2b38a556a72a7f7ac239864820160926,xy3f2ebf2f523bab1f9540baabe6fdf44a526506f168b8591e0431fd35242dab2520160926");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("channelId", "3000");
			jsonObject.put("params", map);
			json = jsonObject.toJSONString();
			System.out.println(json);
		} else if (url.equals("httpUrlOneIdCardNum")) {
			map.put("param", "xy8a70772fe8f346de33cc4e675b400ec3ea1bee6c338697d37bd25641f39b357e20160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(">>>>>" + json);
		} else if (url.equals("httpUrlOneTagName")) {
			// map.put("tagName", "FUNUPLEND_002_1");
			// map.put("param",
			// "xy9900f71f4c0ed27902d44a989b1f6e4e9936fb6f003f90bdc6f44c65ac44abe920160926");
			// map.put("tagName", "FUNUPLEND_002_2");
			// map.put("param",
			// "xy61e03790cc0e8ef23ff264b5b31fd8a83c5173ea2b38a556a72a7f7ac239864820160926");
			// map.put("tagName", "FUNUPLEND_002_3");
			// map.put("param",
			// "xyc3ded03f38bce9225f67a8d54abd8ca3f6307ee39d4a61b768b5a2377bd6f2ae20160926");
			// map.put("tagName", "FUNUPLEND_016_4");
			// map.put("param", "110226196411051919");
			// map.put("tagName", "FUNUPLEND_016_5");
			// map.put("param",
			// "xyc3ded03f38bce9225f67a8d54abd8ca3f6307ee39d4a61b768b5a2377bd6f2ae20160926");
			// map.put("tagName", "FUNUPLEND_016_6");
			// map.put("param", "");
			// map.put("tagName", "FUNUPLEND_017_4");
			// map.put("param", "6");
			// map.put("tagName", "FUNUPLEND_017_5");
			// map.put("param", "6");
			// map.put("tagName", "FUNUPLEND_017_6");
			// map.put("param", "25");
			// map.put("tagName", "FUNUPLEND_018_4");
			// map.put("param", "6");
//			map.put("tagName", "FUNUPLEND_018_5");
//			map.put("param", "6");
//			 map.put("tagName", "FUNUPLEND_018_6");
//			 map.put("param", "25");
			
			req.setChannelId("3001");
			 map.put("tagName", "MoServer");
			 map.put("mobile", "15284813333");
		
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlMultiTagName")) {
			map.put("tagNames", "FUNUPLEND_018_4,FUNUPLEND_018_5,FUNUPLEND_017_4");
			map.put("param", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(">>>>>" + json);
		} else if (url.equals("httpUrlTagType")) {
			map.put("tagType", "balance_of_account");
			map.put("param", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(">>>>>" + json);
		} else if (url.equals("httpUrlTagWithCurrency")) {
			req.setChannelId("3000");
			map.put("param", "6");
			// map.put("reportNo", "234");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		// json =
		// "{\"channelId\":\"102\",\"delayCount\":0,\"needDebugInfo\":true,\"params\":{\"applyNo\":\"1022017072122770001\"},\"singleParamKey\":\"applyNo\",\"singleParamValue\":\"1022017072122770001\",\"sync\":false,\"tagIds\":\"3,4,5\"}";
		return json;
	}

	public static void main(String[] args) {
		TagQueryControllerParam pc = new TagQueryControllerParam();
		// pc.testMultiIdCardNum();
		// System.err.println("--------------------------------------------------");
		// pc.testOneIdCardNum();
		// System.err.println("--------------------------------------------------");
		pc.testOneTagName();
//		System.err.println("--------------------------------------------------");
//		pc.testMultiTagName();
//		System.err.println("--------------------------------------------------");
//		pc.testTagType();
//		System.err.println("--------------------------------------------------");
//		pc.testTagWithCurrency();
//		System.err.println("--------------------------------------------------");
	}
}
