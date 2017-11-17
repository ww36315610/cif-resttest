package com.cif.winds.beans;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;

import com.alibaba.fastjson.JSON;

/**
 * Created by Martin on 2017/4/28.
 */
@Data
public class TagsRequest {
	@Getter
	private String channelId;
	// password
	private String tagIds;
	@Getter
	private String resultId;
	private Map<String, String> params;
	private String verifyType = null;
	private int delayCount = 0;
	// 同步异步方式(true:异步计算,true:同步计算)
	private boolean isSync = false;
	private boolean needDebugInfo = true;

	public void addParam(String key, String value) {
		if (params == null) {
			params = new HashMap();
		}
		params.put(key, value);
	}

	public String getSingleParamKey() {
		if (params != null && params.keySet().size() == 1) {
			return params.keySet().toArray()[0].toString();
		}
		return null;
	}

	public String getSingleParamValue() {
		if (params != null && params.keySet().size() == 1) {
			return params.values().toArray()[0].toString();
		}
		return null;
	}

	public static void main(String[] args) {
		TagsRequest tr = new TagsRequest();
		Map<String, String> params = new HashMap<String, String>();
		params.put("aa", "aaa");
		params.put("bb", "bbb");
		tr.setChannelId("11");
		tr.setDelayCount(1);
		tr.setNeedDebugInfo(true);
		tr.setParams(params);
		System.out.println(tr.toString());
		System.out.println(JSON.toJSON(tr));
	}
}
