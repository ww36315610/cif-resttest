package com.cif.utils.httpclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;

public class QueryDataT {
	private String httpUrl;
	List<Object> paramKey;
	List<Object> paramValue;
	HttpClientImp hcdi = new HttpClientImp();
	HttpClient client = new DefaultHttpClient();
	Map<String, Object> map = new HashMap<String, Object>();
	JSONArray jsonHttp;
	JSONObject jsonObject;
	String json;

	public QueryDataT(String httpUrl, Map<String, Object> header, List<Object> paramKey, List<Object> paramValue) {
		this.httpUrl = httpUrl;
		this.map = header;
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}

	public QueryDataT(String httpUrl, Map<String, Object> header, String json) {
		this.httpUrl = httpUrl;
		this.map = header;
		this.json = json;
	}
	
	public QueryDataT(String httpUrl, Map<String, Object> header) {
		this.httpUrl = httpUrl;
		this.map = header;
	}

	public JSONArray getJsonArrayByGet() {
		jsonHttp = hcdi.getHttp(client, httpUrl, map);
		return jsonHttp;
	}

	public JSONArray getJsonArrayByJson() {
		jsonHttp = hcdi.postHttp(client, httpUrl, map, json);
		return jsonHttp;
	}

	public JSONArray getJsonArrayByList() {
		jsonHttp = hcdi.postHttp(client, httpUrl, map, paramKey, paramValue);
		return jsonHttp;
	}

	public JSONObject getJsonObject() {
		jsonObject = hcdi.postHttpJSON(client, httpUrl, map, json);
		return jsonObject;
	}
}