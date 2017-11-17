package com.cif.winds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class ProController_ReadFile {

	// dev环境
	// private static final String httpUrl =
	// "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/submitTask";
	// private static final String asyncTagResultURL =
	// "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/asyncTagResult";
	// private static final String autoUrl =
	// "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";

	// 生产环境
	private static final String httpUrl = "https://api.puhuifinance.com/cif-utc-rest-pre/api/v1/submitTask";
	private static final String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";

	static Map<String, Object> map = new HashMap<String, Object>();
	static OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
	static List<String> listReadString;
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		// liunx路径
//		 listReadString = FileOperation.readFileByLineString("./conf/idCardNum.txt");
		listReadString = FileOperation.readFileByLineString("F:\\repay_customer_info.txt");
	
	}
	int i = 1;

	public void method() {
		RestfulDao rd = new RestfullDaoImp();

		listReadString.forEach(s -> {
			if (i % 100 == 0) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + i);
			}
			i++;
			rd.getJsonArray(httpUrl, map, getTagsRequest(s));
		});
	}

	public String getTagsRequest(String idCardNum) {
		// 构建参数实体
		TagsRequest req = new TagsRequest();
		req.setChannelId("200");
		req.setTagIds("2001");
		req.addParam("idCardNum", idCardNum);
		String json = JSONObject.toJSONString(req);
		return json;
	}

	public static void main(String[] args) {
		ProController_ReadFile pc = new ProController_ReadFile();
		pc.method();
	}
}
