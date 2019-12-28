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

public class UAT_TagQueryController_4000 implements Runnable {
	String httpUrlCalcTagWithIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/calcTagWithIdCardNum";
	String httpUrlMultiIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	String httpUrlOneIdCardNum = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	String httpUrlOneTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";

	// String httpUrlOneTagName =
	// "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	String httpUrlMultiTagName = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	// String httpUrlTagType =
	// "http://ut1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/tagType";
	private static final String httpUrlTagType = "http://cif-utc-rest.cif.dev/api/v1/moxie";
	// String httpUrlTagType =
	// "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/tagType";

	String httpUrlTagWithCurrency = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/currencyTag";
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		// String autoUrl =
		// "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";

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
		req.setChannelId("4000");
		if (url.equals("httpUrlMultiIdCardNum")) {
			map.put("idCardNums",
					"xy5d7fd593582e54cdbeaf1b6b02072f471e76b3ef271947443a6992c6d372723620160926,11022119811222061X,xyf7a7959df4c515636420dd80ba8ca9025a9ac421d69d392a8e2f8ae788449a9e20160926");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlOneIdCardNum")) {
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		} else if (url.equals("httpUrlOneTagName")) {
			map.put("tagName", "FL_LR_id'");
			map.put("requestId", "882441");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			json = "{\"channelId\":\"4000\",\"params\":{\"requestId\":\"882441\",\"tagName\":\"FL_LR_id\"},\"sync\":false}";
		} else if (url.equals("httpUrlMultiTagName")) {
			map.put("tagNames",
					"FUNUPLEND_002_1,FUNUPLEND_002_2,FUNUPLEND_002_3,FUNUPLEND_016_4,FUNUPLEND_016_5,FUNUPLEND_016_6,FUNUPLEND_017_4,FUNUPLEND_017_5,FUNUPLEND_017_6");
			map.put("idCardNum", "11022119811222061X");
			map.put("id", "6");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			json = "{\"channelId\":\"4000\",\"needDebugInfo\":true,\"params\":{\"requestId\":\"1571\",\"tagNames\":\"获取submit_time,指标-信用卡逾期超90天\"},\"sync\":false}";

		} else if (url.equals("httpUrlTagType")) {
			req.setChannelId("6000");
			// map.put("tagType", "征信");
			// map.put("tagType", "基础");
			// map.put("requestId", "1571");
			// map.put("reportNo", "2015111718000100000001");
			map.put("tagType", "XHD");
			map.put("requestId", "999201603291358002");

			req.setParams(map);
			json = JSONObject.toJSONString(req);
			json = "{\"channelId\":\"4000\",\"params\":{\"requestId\":\"1571\",\"tagType\":\"征信\",\"reportNo\":\"2015111718000100000001\"},\"sync\":false}";
			json ="{\"tagType\":\"fund\",\"mvalue\":\"94fef7b10c72740fdb2f8959c588f272\",\"mkey\":\"user_id\",\"channelId\":\"3003\"}";
			
			json="{\"tagType\":\"insurance\",\"mvalue\":\"94fef7b10c72740fdb2f8959c588f272\",\"mkey\":\"user_id\",\"channelId\":\"3003\"}";
			System.out.println(json);
			// json =
			// "{\"channelId\":\"3001\",\"delayCount\":0,\"needDebugInfo\":true,\"params\":{\"idCardNum\":\"110119198903031100\",\"tagType\":\"bestbuy\"},\"sync\":false}";
		} else if (url.equals("httpUrlTagWithCurrency")) {
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			json = "{\"params\":{\"tagType\":\"fund\",\"mvalue\":\"94fef7b10c72740fdb2f8959c588f272\",\"mkey\":\"user_id\",\"channelId\":\"3003\"},\"channelId\":\"3003\"}";
		} else if (url.equals("httpUrlCalcTagWithIdCardNum")) {
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		return json;
	}

	static UAT_TagQueryController_4000 pc = new UAT_TagQueryController_4000();

	public static void main(String[] args) {
		// pc.testOneTagName();
		// pc.testMultiTagName();
		pc.testTagType();
		// pc.testTagWithCurrency();
		// pc.testMultiIdCardNum();
		// pc.testOneIdCardNum();
		// pc.testCalcTagWithIdCardNum();
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
