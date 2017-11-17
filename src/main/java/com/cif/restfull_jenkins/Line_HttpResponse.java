package com.cif.restfull_jenkins;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.HttpClientImp;
import com.cif.utils.httpclient.Oauth;

public class Line_HttpResponse {
	// 因为常用所以需要把这些静态对象改成非静态【不然会常占用资源不释放】
	// static HttpClientImp httpCL = new HttpClientImp();
	// static HttpClient client = new DefaultHttpClient();
	// 生产Oauth
	static OAuth2AccessToken token = Oauth.getTokenLine();

	public static JSONArray getResponse(JSONObject jsonObjects, String change) {
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		// 线上resfull接口地址
		String before = "https://api.puhuifinance.com/cif-rest-server/";
		// 线上resfull接口地址 --预发布
		// String before ="https://api.puhuifinance.com/cif-rest-server-pre/";

		String url = before + jsonObjects.getString("url");
		String params = jsonObjects.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonArrayHttp = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
		// System.out.println(jsonArrayHttp);
		return jsonArrayHttp;
	}

	public static Map<String, Object> getAuthMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
		return map;
	}

	public static void main(String[] args) {
		String url = "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		String json = "{\"idCardNum\":\"522101198912184431\",\"type\":\"credit_card_info.credit_basic\"}";
		// for (int i = 0; i < 50; i++) {
		// try {
		// Thread.sleep(5);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println("【" + i + "】" + getResponseJmeter());
		// }

		System.out.println("【" + 1 + "】" + compare().get("tag"));
		System.out.println("【" + 2 + "】" + comparerPre().get("tag"));
		System.out.println(compare().get("tag").equals(comparerPre().get("tag")));

		// String columUrl =
		// "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		// String countUrl =
		// "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumnCount";

		// String columUrlPre =
		// "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumn";
		// String countUrlPre =
		// "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumnCount";
		// System.out.println("[1]" + compare(countUrlPre));
	}

	public static JSONObject getResponseJmeter() {
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		String url = "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		String json = "{\"idCardNum\":\"522101198912184431\",\"type\":\"credit_card_info.credit_basic\"}";
		return (JSONObject) httpCL.postHttp(client, url, getAuthMap(), json).get(0);
	}

	public static JSONObject compare() {
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		String url = "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumn";
		// String json =
		// "{\"idCardNum\":\"522101198912184431\",\"type\":\"credit_card_info.credit_basic\"}";
		String json = "{\"idCardNum\":\"xybf158e78de42ac471557e862467838b6277b8d8f924dd0c4b869b4b41f5848f220160926\",\"type\":\"credit_report_info.info_other_overdue\"}";
		return (JSONObject) httpCL.postHttp(client, url, getAuthMap(), json).get(0);
	}

	public static JSONObject comparerPre() {
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		// String url =
		// "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		String url = "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumn";
		// String json =
		// "{\"idCardNum\":\"522101198912184431\",\"type\":\"credit_card_info.credit_basic\"}";
		String json = "{\"idCardNum\":\"xybf158e78de42ac471557e862467838b6277b8d8f924dd0c4b869b4b41f5848f220160926\",\"type\":\"credit_report_info.info_other_overdue\"}";
		return (JSONObject) httpCL.postHttp(client, url, getAuthMap(), json).get(0);
	}

	public static JSONObject compareParam(JSONObject jsonObject, String change) {
		String url = "https://api.puhuifinance.com/cif-rest-server/api/v1/getOneBasicColumn";
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		String params = jsonObject.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonA = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
		if (jsonA.size() > 0) {
			return (JSONObject) httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString()).get(0);
			// System.err.println(jj);
		}
		return null;
	}

	public static JSONObject comparerPreParam(JSONObject jsonObject, String change) {
		String url = "https://api.puhuifinance.com/cif-rest-server-pre/api/v1/getOneBasicColumn";
		HttpClientImp httpCL = new HttpClientImp();
		HttpClient client = new DefaultHttpClient();
		String params = jsonObject.getString("params");
		JSONObject paramsJson = JSONObject.parseObject(params);
		if (paramsJson.getString("idCardNum").contains("idCardNum_change")) {
			paramsJson.replace("idCardNum", change);
		}
		JSONArray jsonA = httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString());
		if (jsonA.size() > 0) {
			return (JSONObject) httpCL.postHttp(client, url, getAuthMap(), paramsJson.toJSONString()).get(0);
			// System.err.println(jj);
		}
		return null;
	}
}
