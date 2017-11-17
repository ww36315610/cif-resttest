package com.cif.winds.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cif.now.utils.PropersTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;

public class Line_ChannelController {
	private static final String httpUrlMultiIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiIdCardNum";
	private static final String httpUrlOneIdCardNum = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneIdCardNum";
	private static final String httpUrlOneTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/oneTagName";
	private static final String httpUrlMultiTagName = "https://api.puhuifinance.com/cif-utc-rest/api/v1/multiTagName";
	private static final String httpUrlTagType = "https://api.puhuifinance.com/cif-utc-rest/api/v1/tagType";
	private static final String httpUrlTagWithCurrency = "https://api.puhuifinance.com/cif-utc-rest/api/v1/currencyTag";
	private static final String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
	TagsRequest tr = new TagsRequest();
	static Map<String, Object> map = new HashMap<String, Object>();
	static OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
	RestfulDao rd = new RestfullDaoImp();
	static {
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}

	public void multiIdCardNum(String json) {
		System.out.println(rd.getJsonArray(httpUrlMultiIdCardNum, map, json));
	}

	public void oneIdCardNum(String json) {
		System.out.println(rd.getJsonArray(httpUrlOneIdCardNum, map, json));
	}

	public void oneTagName(String json) {
		System.out.println(rd.getJsonArray(httpUrlOneTagName, map, json));
	}

	public void multiTagName(String json) {
		System.out.println(rd.getJsonArray(httpUrlMultiTagName, map, json));
	}

	public void tagType(String json) {
		System.out.println(rd.getJsonArray(httpUrlTagType, map, json));
	}

	public void tagWithCurrency(String json) {
		System.out.println(rd.getJsonArray(httpUrlTagWithCurrency, map, json));
	}

	// 通过反射动态获取执行方法
	public void reflectionMethod(String methodName, String json) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getClass().getMethod(methodName, String.class);
		method.invoke(this, json);
	}

	// 获取发方法名
	public static String getMethod(String url) {
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.length());
		return methodName;
	}

	public static void main(String[] args) {
		Line_ChannelController ut = new Line_ChannelController();
		try {
			ut.reflectionMethod(getMethod(httpUrlTagType), PropersTools.getValue("jsonType"));
			// ut.reflectionMethod(getMethod(httpUrlOneTagName),
			// PropersTools.getValue("jsonTagName"));
			// ut.reflectionMethod(getMethod(httpUrlMultiTagName),PropersTools.getValue("jsonMultiTagName"));
			// ut.reflectionMethod(getMethod(httpUrlMultiIdCardNum),
			// PropersTools.getValue("jsonMultiIdCardNum"));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
