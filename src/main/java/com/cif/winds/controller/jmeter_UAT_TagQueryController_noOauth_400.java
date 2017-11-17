package com.cif.winds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Maps;

public class jmeter_UAT_TagQueryController_noOauth_400 {
	static String httpUrlMultiIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/multiIdCardNum";
	static String httpUrlOneIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/oneIdCardNum";
	static String httpUrlOneTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/oneTagName";
	static String httpUrlMultiTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/multiTagName";
	static String httpUrlTagType = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/tagType";
	// static String httpUrlTagWithCurrency =
	// "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/tagWithCurrency";
	static String httpUrlTagWithCurrency = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/aapi/v1/currencyTag";
	static Map<String, Object> map = new HashMap<String, Object>();
	static RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getToken(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		// map.put("Authorization", String.format("%s %s", token.getTokenType(),
		// token.getValue()));
		map.put("Content-Type", "application/json");
	}

	public static JSONArray testMultiIdCardNum() {
		return rd.getJsonArray(httpUrlMultiIdCardNum, map, getTagsRequest("httpUrlMultiIdCardNum"));
	}

	public static JSONArray testOneIdCardNum() {
		return rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneIdCardNum"));
	}

	public static JSONArray testOneTagName() {
		return rd.getJsonArray(httpUrlOneTagName, map, getTagsRequest("httpUrlOneTagName"));
	}

	public static JSONArray testMultiTagName() {
		return rd.getJsonArray(httpUrlMultiTagName, map, getTagsRequest("httpUrlMultiTagName"));
	}

	public static JSONArray testTagType() {
		return rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType"));
	}

	public static JSONArray testTagWithCurrency() {
		return rd.getJsonArray(httpUrlTagWithCurrency, map, getTagsRequest("httpUrlTagWithCurrency"));
	}

	public static String getTagsRequest(String url) {
		String json = null;
		Map<String, String> map = Maps.newHashMap();
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("4000");
		if (url.equals("httpUrlMultiIdCardNum")) {
			map.put("idCardNums",
					"11022119811222061X,xyf7a7959df4c515636420dd80ba8ca9025a9ac421d69d392a8e2f8ae788449a9e20160926");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("httpUrlOneIdCardNum")) {
			map.put("idCardNum", "11022119811222061X");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("httpUrlOneTagName")) {
			map.put("tagName", "FUNUPLEND_017_5");
			map.put("idCardNum", "11022119811222061X");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("httpUrlMultiTagName")) {
			map.put("tagNames",
					"FUNUPLEND_002_1,FUNUPLEND_002_2,FUNUPLEND_002_3,FUNUPLEND_016_4,FUNUPLEND_016_5,FUNUPLEND_016_6,FUNUPLEND_017_4,FUNUPLEND_017_5,FUNUPLEND_017_6");
			map.put("idCardNum", "11022119811222061X");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("httpUrlTagType")) {
			map.put("tagType", "MA_get_data");
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("httpUrlTagWithCurrency")) {
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			// System.out.println(json);
		} else if (url.equals("test")) {
			map.put("requestId", "5");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		return json;
	}

	// jmeter压测
	public static JSONArray jmeterOneIdCardNum() {
		return rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneIdCardNum"));
	}

	public static void main(String[] args) {
		// System.out.println(testMultiIdCardNum());
		// System.out.println(testOneIdCardNum());
		// System.out.println(testOneTagName());
		// System.out.println(testMultiTagName());
		System.out.println(testTagType());
		System.out.println(testTagWithCurrency());

	}
}