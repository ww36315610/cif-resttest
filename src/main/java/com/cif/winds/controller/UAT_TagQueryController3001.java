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

public class UAT_TagQueryController3001 {

	private static final String httpUrlTagType = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/basicInfo";
	static Map<String, Object> map = new HashMap<String, Object>();
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

	public static String getTagsRequest(String url) {
		String json = null;
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		if (url.equals("httpUrlTagType")) {
			req.setChannelId("3001");
			Map<String, String> map = Maps.newHashMap();
			// map.put("idCardNum", "110119198903031100");
			map.put("idCardNum", "xy5db5b88fc86fa751113e1cf4d281a5275540af8cf4acf3dbe2e2c39354b421cf20160926");
			/**
			 * xy5db5b88fc86fa751113e1cf4d281a527d97459fa982eb31e483e72a861169e0720160926
			 * xy5db5b88fc86fa751113e1cf4d281a5275540af8cf4acf3dbe2e2c39354b421cf20160926
			 * xy5db5b88fc86fa751113e1cf4d281a5272c7c657e391414cc00480d2d788e379820160926
			 * xy5db5b88fc86fa751113e1cf4d281a527427786b0d9332fb94b2ba0f2793da6f820160926
			 * xy5db5b88fc86fa751113e1cf4d281a5271127fcdb82a948def64895625437717a20160926
			 * xy5db5b88fc86fa751113e1cf4d281a52781ae8beb3230319342c5ddb25672784e20160926
			 * xy5db5b88fc86fa751113e1cf4d281a5275782dc8af6b3b537e3cf2838672ec8d920160926
			 * xy5db5b88fc86fa751113e1cf4d281a5270c07ab928ed18edbe6ab3fa6a37bcffe20160926
			 * xy5db5b88fc86fa751113e1cf4d281a527f8acfef6dfa1879de0da37a8909a675d20160926
			 * xy5db5b88fc86fa751113e1cf4d281a52755dfee3e39745476393c85544ba3270520160926
			 * xy5db5b88fc86fa751113e1cf4d281a527bc9c33307492bf94245e9a3b2d2ffd6e20160926
			 */
			// map.put("channel", "bestbuy");
			map.put("channel", "basic_qz_channel");
			// map.put("channel", "basic_qz-self-support");
			// map.put("channel", "basic_nirvana");

			req.setParams(map);
			json = JSONObject.toJSONString(req);
			System.err.println(json);
		}
		return json;
	}

	public static void main(String[] args) {
		RestfulDao rd = new RestfullDaoImp();
		System.out.println(rd.getJsonArray(httpUrlTagType, map, getTagsRequest("httpUrlTagType")));
	}
}
