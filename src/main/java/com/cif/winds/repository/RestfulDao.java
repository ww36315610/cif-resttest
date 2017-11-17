package com.cif.winds.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;

@Repository
public interface RestfulDao {
	public JSONArray getJsonArray(String idNo);

	public JSONArray getJsonArray(String httpUrl,Map<String, Object> map, List<Object> listKey, List<Object> listValue);

	public JSONArray getJsonArray(String httpUrl,Map<String, Object> map, String json);
}
