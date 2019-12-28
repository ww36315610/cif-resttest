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

public class TagQueryController_4000 implements Runnable {
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	String httpUrlMultiIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	String httpUrlOneIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	String httpUrlOneTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	String httpUrlMultiTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	String httpUrlTagType = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	String httpUrlTagWithCurrency = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagWithCurrency";

	// String httpUrlMultiIdCardNum =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/multiIdCardNum";
	// String httpUrlOneIdCardNum =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/oneIdCardNum";
	// String httpUrlOneTagName =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/oneTagName";
	// String httpUrlMultiTagName =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/multiTagName";
	// String httpUrlTagType =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/tagType";
	// String httpUrlTagWithCurrency =
	// "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/tagWithCurrency";

	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";

		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		System.out.println(token.getTokenType());
		System.out.println(token.getValue());
		map.put("Content-Type", "application/json");
		System.out.println(map);
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
			json = "{\"channelId\":\"4000\",\"params\":{\"requestId\":\"6\",\"tagName\":\"FL_LR_id\"},\"sync\":false}";
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
			json = "{\"channelId\":\"4000\",\"params\":{\"requestId\":\"6\",\"tagType\":\"征信\",\"reportNo\":\"2015111718000100000001\"},\"sync\":false}";
			json = "{\"channelId\":\"8000\",\"delayCount\":0,\"needDebugInfo\":true,\"params\":{\"requestId\":\"35\",\"tagType\":\"jiea\",\"idCardNum\":\"110111198909191416\"},\"sync\":false}";
			// json =
			// "{\"channelId\":\"3001\",\"delayCount\":0,\"needDebugInfo\":true,\"params\":{\"idCardNum\":\"110119198903031100\",\"tagType\":\"bestbuy\"},\"sync\":false}";
		} else if (url.equals("httpUrlTagWithCurrency")) {
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
			json = "{\"channelId\":\"6000\",\"params\":{\"requestId\":\"1571\",\"reportNo\":\"2015111718000100000001\"},\"sync\":false}";
		} else if (url.equals("httpUrlCalcTagWithIdCardNum")) {
			map.put("requestId", "1571");
			map.put("reportNo", "2015111718000100000001");
			req.setParams(map);
			json = JSONObject.toJSONString(req);
		}
		return json;
	}

	static TagQueryController_4000 pc = new TagQueryController_4000();

	public static void main(String[] args) {
		// for (int i = 0; i < 10; i++) {
		// pc.testOneTagName();
		// pc.testTagType();
		// pc.testOneTagName();
		// pc.testTagType();
		// pc.testOneTagName();
		// pc.testTagType();
		// pc.testOneTagName();
		// pc.testOneTagName();
		// pc.testTagType();
		// pc.testOneTagName();
		// }
		// pc.testTagWithCurrency();
		// pc.testMultiIdCardNum();
		// pc.testOneIdCardNum();
		// pc.testCalcTagWithIdCardNum();
		// System.err.println("--------------------------------------------------");
		// for (int i = 0; i < 20; i++) {
		// Thread tr = new Thread(pc);
		// tr.start();
		// }
		// Thread th = new Thread(pc);
		// th.start();
		// Thread th1 = new Thread(pc);
		// th1.start();
		for (int num = 0; num < 1; num++) {
			new Thread(pc).start();
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 1; i++) {
			testOneTagName();
			testTagType();
		}
	}
}
