package com.cif.winds.repository;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.QueryDataT;

public class RestfullDaoImp implements RestfulDao {

	@Override
	public JSONArray getJsonArray(String idNo) {
		return null;
	}

	@Override
	public JSONArray getJsonArray(String httpUrl, Map<String, Object> map, List<Object> listKey, List<Object> listValue) {
		QueryDataT qd = new QueryDataT(httpUrl, map, listKey, listValue);
		return qd.getJsonArrayByList();
	}

	@Override
	public JSONArray getJsonArray(String httpUrl, Map<String, Object> map, String json) {
		QueryDataT qd = new QueryDataT(httpUrl, map, json);
		return qd.getJsonArrayByJson();
	}

	@Override
	public JSONObject getJsonObject(String httpUrl, Map<String, Object> map, String json) {
		QueryDataT qd = new QueryDataT(httpUrl, map,json);
		return qd.getJsonObject();
	}

	@Override
	public JSONArray getJsonArrayGet(String httpUrl, Map<String, Object> map) {
		QueryDataT qd = new QueryDataT(httpUrl, map);
		return qd.getJsonArrayByGet();
	}
}
