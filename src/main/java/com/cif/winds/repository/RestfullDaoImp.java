package com.cif.winds.repository;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.httpclient.QueryDataT;

public class RestfullDaoImp implements RestfulDao {

	@Override
	public JSONArray getJsonArray(String idNo) {
		return null;
	}

	@Override
	public JSONArray getJsonArray(String httpUrl,Map<String, Object> map, List<Object> listKey, List<Object> listValue) {
		QueryDataT qd = new QueryDataT(httpUrl, map, listKey, listValue);
		return qd.getJsonArrayByList();
	}

	@Override
	public JSONArray getJsonArray(String httpUrl,Map<String, Object> map, String json) {
		QueryDataT qd = new QueryDataT(httpUrl, map, json);
		return qd.getJsonArrayByJson();
	}
}
