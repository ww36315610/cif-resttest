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

public class Dev_TagQueryController implements Runnable {
	String httpUrlCalcTagWithIdCardNum = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/calcTagWithIdCardNum";
	String httpUrlMultiIdCardNum = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/multiIdCardNum";
	String httpUrlOneIdCardNum = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/oneIdCardNum";
	String httpUrlOneTagName = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/oneTagName";
	String httpUrlMultiTagName = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/multiTagName";
	String httpUrlTagType = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/tagType";
	String httpUrlTagWithCurrency = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/currencyTag";
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
	}

	public void testCalcTagWithIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlCalcTagWithIdCardNum, map,
				getTagsRequest("httpUrlCalcTagWithIdCardNum")));
	}

	public void testMultiIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlMultiIdCardNum, map, getTagsRequest("httpUrlMultiIdCardNum")));
	}

	public void testOneIdCardNum() {
		System.out.println(rd.getJsonArray(httpUrlOneIdCardNum, map, getTagsRequest("httpUrlOneIdCardNum")));
	}

	public void testOneTagName() {
		System.out.println(rd.getJsonArray(httpUrlOneTagName, map, getTagsRequest("httpUrlOneTagName")));
	}

	public void testMultiTagName() {
		System.out.println(rd.getJsonArray(httpUrlMultiTagName, map, getTagsRequest("httpUrlMultiTagName")));
	}

	public void testTagType() {
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}

	public void testTagWithCurrency() {
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
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.put("channelId", "3000");
			// jsonObject.put("params", map);
			// json = jsonObject.toJSONString();
		} else if (url.equals("httpUrlOneIdCardNum")) {
			// map.put("idCardNum", "11022119811222061X");
			// map.put("idCardNum",
			// "xyf7a7959df4c515636420dd80ba8ca9025a9ac421d69d392a8e2f8ae788449a9e20160926");
			// map.put("idCardNum", "123123");
			map.put("id", "6");
			// map.put("id", "5");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlOneTagName")) {
			map.put("tagName", "FUNUPLEND_002_1");
//			map.put("idCardNum", "11022119811222061X");
			map.put("param", "110112199001012221");
//			map.put("param", "110112199001012221");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlMultiTagName")) {
			map.put("tagNames",
					"FUNUPLEND_002_1,FUNUPLEND_002_2,FUNUPLEND_002_3,FUNUPLEND_016_4,FUNUPLEND_016_5,FUNUPLEND_016_6,FUNUPLEND_017_4,FUNUPLEND_017_5,FUNUPLEND_017_6");
			// map.put("idCardNum", "11022119811222061X");
			map.put("param", "110112199001012221");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlTagType")) {
			req.setChannelId("3000");
			 map.put("tagType", "customer");
//			 map.put("tagType", "core_repay_record");
			// map.put("tagType", "lease_repay_record");
			// map.put("tagType", "factoring_repay_record");
			// map.put("tagType", "MA_get_data");
//			map.put("tagType", "456");
			// map.put("tagType", "balance_of_account");
//			 map.put("param","110112199001012221");
			map.put("param", "xyf7a7959df4c515636420dd80ba8ca9025a9ac421d69d392a8e2f8ae788449a9e20160926");
//			map.put("param", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(">>>>>>>"+json);
		} else if (url.equals("httpUrlTagWithCurrency")) {
			req.setChannelId("4000");
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.out.println(">>>>"+json);
		} else if (url.equals("httpUrlCalcTagWithIdCardNum")) {
			req.setChannelId("3000");
			map.put("idCardNum", "xya6ff42fef6e4076ead5ac3d95a6574f420160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		return json;
	}

	static Dev_TagQueryController pc = new Dev_TagQueryController();

	public static void main(String[] args) {
//		pc.testMultiIdCardNum();
//		 pc.testOneIdCardNum();
//		 pc.testOneTagName();
//		 pc.testMultiTagName();
		pc.testTagType();
//		 pc.testTagWithCurrency();
//		pc.testCalcTagWithIdCardNum();
		// System.err.println("--------------------------------------------------");
		// for (int i = 0; i < 20; i++) {
		// Thread tr = new Thread(pc);
		// tr.start();
		// }
	}

	@Override
	public void run() {
		pc.testMultiIdCardNum();
		pc.testOneIdCardNum();
		pc.testOneTagName();
		pc.testMultiTagName();
		pc.testTagType();
	}
}
