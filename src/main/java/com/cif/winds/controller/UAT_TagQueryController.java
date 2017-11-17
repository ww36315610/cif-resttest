package com.cif.winds.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Maps;

public class UAT_TagQueryController {

	private static final String httpUrlCalcTagWithIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/calcTagWithIdCardNum";
	private static final String httpUrlMultiIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	private static final String httpUrlOneIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	private static final String httpUrlOneTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	private static final String httpUrlMultiTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	private static final String httpUrlTagType = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	private static final String httpUrlTagWithCurrency = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/currencyTag";
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getToken(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		System.out.println(map);
	}

	public void calcTagWithIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlCalcTagWithIdCardNum, map,
				getTagsRequest("httpUrlCalcTagWithIdCardNum")));
	}

	public void multiIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlMultiIdCardNum, map, getTagsRequest("httpUrlMultiIdCardNum")));
	}

	public void oneIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneIdCardNum")));
	}

	public void oneTagName() {
		System.out.println(rd.getJsonArray(httpUrlOneTagName, map, getTagsRequest("httpUrlOneTagName")));
	}

	public void multiTagName() {
		System.out.println(rd.getJsonArray(httpUrlMultiTagName, map, getTagsRequest("httpUrlMultiTagName")));
	}

	public void tagType() {
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}

	public void tagWithCurrency() {
		System.out.println(rd.getJsonArray(httpUrlTagWithCurrency, map, getTagsRequest("httpUrlTagWithCurrency")));
	}

	public String getTagsRequest(String url) {
		String json = null;
		Map<String, String> map = Maps.newHashMap();
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("3000");
		if (url.equals("httpUrlMultiIdCardNum")) {
			map.put("idCardNums",
					"xy5d7fd593582e54cdbeaf1b6b02072f471e76b3ef271947443a6992c6d372723620160926,11022119811222061X,xyf7a7959df4c515636420dd80ba8ca9025a9ac421d69d392a8e2f8ae788449a9e20160926");
			// map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlOneIdCardNum")) {
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlOneTagName")) {
			map.put("tagName", "FUNUPLEND_020_1");
			map.put("param", "xyb46c4d49b488627e0f144daabf50356340c645e3f68c97de0486e1d1a9e11d9420160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlMultiTagName")) {
			map.put("tagNames", "FUNUPLEND_016_4,FUNUPLEND_016_5,FUNUPLEND_016_6");
			map.put("param", "xyc3ded03f38bce9225f67a8d54abd8ca3f6307ee39d4a61b768b5a2377bd6f2ae20160926");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(json);
			// json
			// ="{\"param\":\"130921198209272258\",\"tagNames\":\"FUNUPLEND_002_1,FUNUPLEND_016_4,FUNUPLEND_020_1\"}}";
			// String
			// json1="{\"param\":\"6\",\"dueDate\":\"2014-03-10\",\"tagNames\":\"FUNUPLEND_017_4,FUNUPLEND_017_5,FUNUPLEND_018_4,FUNUPLEND_019_2\"}}";
		} else if (url.equals("httpUrlTagType")) {
			req.setChannelId("3000");
			map.put("tagType", "asset_overview");
			map.put("param", "xyb46c4d49b488627e0f144daabf50356340c645e3f68c97de0486e1d1a9e11d9420160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(">>>>>>>" + json);
		} else if (url.equals("httpUrlTagWithCurrency")) {
			req.setChannelId("4000");
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(">>>>" + json);
		} else if (url.equals("httpUrlCalcTagWithIdCardNum")) {
			req.setChannelId("3000");
			map.put("idCardNum", "xya6ff42fef6e4076ead5ac3d95a6574f420160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		return json;
	}

	static UAT_TagQueryController pc = new UAT_TagQueryController();

	// 通过反射动态获取执行方法
	public void reflectionMethod(String methodName) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getClass().getMethod(methodName);
		method.invoke(this);
	}

	// 获取发方法名
	public static String getMethod(String url) {
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.length());
		return methodName;
	}

	public static void main(String[] args) {
		UAT_TagQueryController ut = new UAT_TagQueryController();
		System.err.println(UAT_TagQueryController.class.getName());
		try {
			// for (int i = 0; i < 10; i++) {
			ut.reflectionMethod(getMethod(httpUrlTagType));
			// ut.reflectionMethod(getMethod(httpUrlMultiTagName));
			// ut.reflectionMethod(getMethod(httpUrlMultiIdCardNum));
			// }
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
