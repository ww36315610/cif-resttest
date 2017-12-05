package com.cif.winds.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class UAT_Exception {
	private static String beforeUrl = "http://cif-utc-rest.cif.test/test/v1/";
	private static String url;
	private static String afterUrl = "&message=";

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

	public static List<String> getMessage(String filePath) {

		return FileOperation.readFileByLineString(filePath);
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {
		RestfulDao rd = new RestfullDaoImp();
		String fileName = "fileException.txt";
		String filePath = UAT_Exception.class.getClassLoader().getResource(fileName).getPath();
		int i = 4000;
		for (String exception : getMessage(filePath)) {
			url = null;
			url = beforeUrl + "generateException?exceptionClass=" + exception.trim() + afterUrl
					+ exception.trim().substring(exception.trim().lastIndexOf(".") + 1);
			 System.out.println(url);
			 System.out.println(rd.getJsonArrayGet(url, map));
//			url = null;
//			url = beforeUrl + "timeout?time=" + (i++);
//			System.err.println(url);
//			System.err.println(rd.getJsonArrayGet(url, map));
		}
	}
}
