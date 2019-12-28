package com.cif.now.mongo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.utils.resource.HttpClientTools;
import com.cif.utils.httpclient.Oauth;

public class L_HttpResponse {
	// 因为常用所以需要把这些静态对象改成非静态【不然会常占用资源不释放】
	// static HttpClientImp httpCL = new HttpClientImp();
	// static HttpClient client = new DefaultHttpClient();
	// 生产Oauth
	static OAuth2AccessToken token = Oauth.getTokenLine();

	public static Map<String, Object> getAuthMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}

	public static JSONObject compareParam(JSONObject jsonObject, String change) {
		String url = "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		String params = jsonObject.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		String jsonString = null;
		JSONObject json = null;
		try {
			jsonString = HttpClientTools.postHttp(url, getAuthMap(), paramsJson.toJSONString());
			json = JSONObject.parseObject(jsonString);
		} catch (Exception e) {
			System.err.println("params:[" + params + "]" + jsonString);
		}
		return json;
	}

	public static JSONObject comparerPreParam(JSONObject jsonObject, String change) {
		String url = "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumn";
		String params = jsonObject.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		String jsonString = null;
		JSONObject json = null;
		try {
			jsonString = HttpClientTools.postHttp(url, getAuthMap(), paramsJson.toJSONString());
			json = JSONObject.parseObject(jsonString);
		} catch (Exception e) {
			System.err.println("[pre]-params:【" + params + "】" + jsonString);
		}
		return json;
	}

	public static void main(String[] args) {
//		String json = "{\"params\":{\"idCardNum\":\"idCardNum_change\",\"type\":\"mobile_info.contacts_intersectphones\"}}";
		String json = "{\"params\":{\"idCardNum\":\"idCardNum_change\",\"type\":\"mobile_info.contacts_intersectphones\"}}";
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		String change = "xy585af9e3836989a85290fc243a23efd101a4efb0e715d7bdc7f5b295fe88eb0220160926";
//		comparerPreParam(jsonObject, change);
		System.out.println(comparerPreParam(jsonObject, change));
	}
}