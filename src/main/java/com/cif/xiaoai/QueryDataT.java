package com.cif.xiaoai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.httpclient.HttpClientImp;

public class QueryDataT {
	private String httpUrl;
	private String httpFilterKey;
	private String httpFilterValue;
	private String sql;
	private String failString;
	static List<Object> paramKey;
	static List<Object> paramValue;
	static HttpClientImp hcdi = new HttpClientImp();
	static HttpClient client = new DefaultHttpClient();
	static Map<String, Object> map = new HashMap<String, Object>();
	static JSONArray jsonHttp;

	public QueryDataT(String httpUrl, List<Object> paramKey, List<Object> paramValue) {
		this.httpUrl = httpUrl;
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}

	public JSONArray getJsonArray() {
		jsonHttp = hcdi.postHttp(client, httpUrl, map, paramKey, paramValue);
		return jsonHttp;
	}

}
