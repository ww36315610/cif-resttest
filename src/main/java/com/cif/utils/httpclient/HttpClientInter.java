package com.cif.utils.httpclient;

import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface HttpClientInter {
	// get方式， 参数类型为String返回JSONObject的HttpClient接口
	public JSONObject getString(HttpClient client, String url,
			Map<String, Object> map);

	// post方式， 参数类型为String返回JSONObject的HttpClient接口
	public JSONObject postString(HttpClient client, String url,
			Map<String, Object> map, List<Object> paramKey,
			List<Object> paramValue);

	// post方式， 参数类型String为返回JSONArry的HttpClient接口
	public JSONArray postStringA(HttpClient client, String url,
			Map<String, Object> map, List<Object> paramKey,
			List<Object> paramValue);

	// post方式， 参数类型为JSON返回JSONObject的HttpClient接口
	public JSONObject postJson(HttpClient client, String url,
			Map<String, Object> map, String json);

}