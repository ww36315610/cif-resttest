package com.cif.xiaoai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.httpclient.HttpClientImp;

public class QueryData {
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

	static {
		paramKey = new ArrayList<Object>();
		paramKey.add("idNo");
	}

	public QueryData(String httpUrl, String httpFilterKey, String httpFilterValue) {
		this.httpUrl = httpUrl;
		this.httpFilterKey = httpFilterKey;
		this.httpFilterValue = httpFilterValue;
	}

	public JSONArray getJsonArray() {
		paramValue = new ArrayList<Object>();
		paramValue.add(httpFilterValue);
		jsonHttp = hcdi.postHttp(client, httpUrl, map, paramKey, paramValue);
		return jsonHttp;
	}

}
