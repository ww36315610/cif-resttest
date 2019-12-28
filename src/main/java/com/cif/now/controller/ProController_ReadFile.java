package com.cif.now.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.testng.collections.Maps;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.utils.resource.HttpClientTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.google.common.collect.Lists;

public class ProController_ReadFile {

	private static final String SUBMITTASKURL = "https://api.puhuifinance.com/cif-utc-rest/api/v1/submitTask";
	private static final String OAUTHURL = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
	static Map<String, Object> map = Maps.newHashMap();
	static OAuth2AccessToken token = Oauth.getTokenLine(OAUTHURL);
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}
	static int i = 1;

	public static void main(String[] args) {
		HttpClientTools hct = new HttpClientTools();
		List<String> listReadString = Lists.newArrayList();
		// liunx路径
//		listReadString = FileOperation.readFileByLineString("./conf/idCardNum.txt");
		listReadString = FileOperation.readFileByLineString("F:\\repay_customer_info.txt");
		listReadString = FileOperation.readFileByLineString("F:\\idd.txt");
		listReadString.forEach(s -> {
			if (i % 1000 == 0) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + i);
			}
			i++;
			 System.out.println(hct.postHttp(SUBMITTASKURL, map, getTagsRequest(s)));
			});
	}

	public static String getTagsRequest(String idCardNum) {
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("200");
		req.setTagIds("2001");
		req.addParam("idCardNum", idCardNum);
		String json = JSONObject.toJSONString(req);
		return json;
	}
}
