package com.cif.winds.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;

//import org.springframework.data.mongodb.core.mapping.Document;

public class TagsResult {
	private boolean isSuccess;
	private int successCount;
	private Set<String> successTagSet;
	private int failCount;
	private Map<String, String> failTagReasonMap;
	private Date beginTime = new Date();
	private Date endTime;
	private long castTime;
	private String resultId;
	private String channelId;
	private String message;
	private String tagIds;
	private Map<String, String> param = new HashedMap();
	private boolean isSync = false;
	private Map<String, List> resultMap = new HashedMap();
	private Map<String, TagDebugInfo> tagNameDebugInfoMap = new HashedMap();

	public void addTagResult(String key, Object value) {
		if (resultMap.containsKey(key)) {
			resultMap.get(key).add(value);
		} else {
			resultMap.put(key, new ArrayList());
			resultMap.get(key).add(value);
		}
	}

	public void generateId() {
		resultId = UUID.randomUUID().toString().replaceAll("-", "");
	}

	public TagsResult(TagsRequest request) {
		if (request != null) {
			// 复制request中的必要的复制参数
			// 获取resultId({channelId}+{tagIdsList(需要排序)}+{singleParaKey}+{singleParamValue}+{时间戳})
			String requestIdSource = request.getChannelId() + "_" + request.getTagIds() + "_"
					+ request.getSingleParamKey() + "_" + request.getSingleParamValue() + "_" + (new Date()).getTime();
			// resultId = DigestUtils.md5Hex(requestIdSource);

			channelId = request.getChannelId();
			tagIds = request.getTagIds();
			param.putAll(request.getParams());
			isSync = request.isSync();
		}
	}
}
