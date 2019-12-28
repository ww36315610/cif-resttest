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
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public int getDelayCount() {
		return delayCount;
	}

	public void setDelayCount(int delayCount) {
		this.delayCount = delayCount;
	}

	public boolean isSync() {
		return isSync;
	}

	public void setSync(boolean sync) {
		isSync = sync;
	}

	public boolean isNeedDebugInfo() {
		return needDebugInfo;
	}

	public void setNeedDebugInfo(boolean needDebugInfo) {
		this.needDebugInfo = needDebugInfo;
	}

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
