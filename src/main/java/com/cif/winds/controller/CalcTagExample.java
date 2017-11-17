package com.cif.winds.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;

/**
 * 此事例为调用 标签平台 的事例代码
 *
 * 标签平台的WIKI: http://wiki.puhuitech.cn/pages/viewpage.action?pageId=26302747
 *
 * Created by Bernie on 2017/9/19.
 */
public class CalcTagExample {

	// UAT环境地址
	private final static String UAT_BASE_URL = "http://t1.zuul.pub.puhuifinance.com/cif-utc-rest/api/v1/";
	// DEV
	private final static String DEV_BASE_URL = "http://d1.zuul.pub.puhuifinance.com:8765/cif-utc-rest/api/v1/";
	// auth地址
	private final static String AUTH_URL = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";

	/**
	 * 用传入一个标签名 计算该标签的值
	 *
	 * 需要构建一个JSON作为POST调用的参数: chanlelId: 渠道号 params: 是一个map, key代表sql中的变量,
	 * value为此变量的值
	 */
	public static String buildOneTagName() {
		JSONObject requestJSON = new JSONObject();

		requestJSON.put("channelId", "6000");// 此项为必填参数 代表需要计算哪个渠道的标签

		JSONObject params = new JSONObject();
		// tagName: 用此接口调用时, tagName为固定写法, 代表标签名
		params.put("tagName", "5106");
		// 如: SELECT count(1) FROM finup_risk.risk_history_over_duerecord WHERE
		// request_id = '$requestId'
		// requestId为sql中的变量名 $requestId
		params.put("requestId", "79977");

		requestJSON.put("parmas", params);

		return requestJSON.toJSONString();
	}

	private static String asyncTagResult() {
		JSONObject requestJSON = new JSONObject();

		requestJSON.put("channelId", "200");// 此项为必填参数 代表需要计算哪个渠道的标签

		JSONObject params = new JSONObject();
		params.put("resultId", "1111123a6ef6980f1294c21a19c2aab0fdb2712");
		requestJSON.put("params", params);

		return requestJSON.toJSONString();
	}

	private static String async7000() {
		JSONObject requestJSON = new JSONObject();
		// 此项为必填参数 代表需要计算哪个渠道的标签
		requestJSON.put("channelId", "7000");

		JSONObject params = new JSONObject();
		params.put("tagType", "type");
		params.put("calcTime", "2017-09-23");
		requestJSON.put("params", params);

		return requestJSON.toJSONString();
	}

	private static String submitTask() {
		JSONObject requestJSON = new JSONObject();
		// 此项为必填参数 代表需要计算哪个渠道的标签
		requestJSON.put("channelId", "200");
		requestJSON.put("tagIds", "7001,7002,7003");

		JSONObject params = new JSONObject();
		params.put("idCardNum", "xy5d7fd593582e54cdbeaf1b6b02072f471e76b3ef271947443a6992c6d372723620160926");
		requestJSON.put("params", params);
		System.out.println(">>>>>" + requestJSON.toString());
		return requestJSON.toJSONString();
	}

	public static void main(String[] args) {
		// 构建Post请求的Header
		Map<String, String> headParams = headParam(AUTH_URL);

		// 请求URL
		// String url = DEV_BASE_URL+"asyncTagResult";

		// 构建POST请求的参数 针对不同的接口
		// String postParmJson = buildOneTagName();
		// String postParmJson = asyncTagResult();
		// String postParmJson = async7000();

		String url = DEV_BASE_URL + "submitTask";
		String postParmJson = submitTask();

		// 调用 oneTagName 接口
		try {
			String str = httpPost(postParmJson, headParams, url);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String httpPost(String postParmJson, Map<String, String> headParam, String url) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		headParam.forEach((k, v) -> {
			post.setHeader(k, v);
		});
		StringEntity entity = new StringEntity(postParmJson, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = client.execute(post);

		int code = resp.getStatusLine().getStatusCode();
		String str = EntityUtils.toString(resp.getEntity(), EntityUtils.getContentCharSet(resp.getEntity()));
		return str;
	}

	private static Map<String, String> headParam(String url) {
		Map<String, String> map = new HashMap();
		OAuth2AccessToken token = getToken(url);
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}

	private static OAuth2AccessToken getToken(String url) {
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientAuthenticationScheme(AuthenticationScheme.form);
		resource.setAccessTokenUri(url);
		resource.setClientId("cif-utc-rest");
		resource.setClientSecret("cif-utc-rest");
		resource.setGrantType("client_credentials");
		OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
		return accessToken;
	}

}
