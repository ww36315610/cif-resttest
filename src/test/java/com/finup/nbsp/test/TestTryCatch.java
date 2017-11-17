package com.finup.nbsp.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.testng.collections.Maps;

import com.alibaba.fastjson.JSON;
import com.cif.winds.beans.TagsRequest;

public class TestTryCatch {
	public static void main(String[] args) {
		tryCatchTest();
		stringBuilderTest();
		mapTest();

		TagsRequest req = new TagsRequest();
		req.setChannelId("11111");
		SimpleDateFormat sd = new SimpleDateFormat();

		System.out.println(sd.format(new Date()));
		Map<String, String> map = Maps.newHashMap();
		map.put("m1", "m1Value");
		map.put("m2", sd.format(new Date()));
		req.setParams(map);
		System.out.println(getJson(req));
	}

	// 测试try catch
	static void tryCatchTest() {
//		System.out.println(1 / 0);  //如果这样就直接停止程序报异常了
		try {
			System.out.println("111try");
//			System.out.println(1 / 0);
			System.out.println("222try");
		} catch (Exception e) {
			System.out.println("333catch");
		} finally {
			System.out.println("444finally");
		}
		System.out.println("555other");
	}

	// 测试StringBuilder
	static void stringBuilderTest() {
		StringBuilder sb = new StringBuilder();
		sb.append("aaaa");
		sb.append("bbbb");
		System.out.println(sb);
	}

	// 测试map重复key
	static void mapTest() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("m1", "v1");
		map.put("m2", "v2");
		map.put("m3", "v3");
		map.put("m11", "v11");
		System.out.println(map);
		System.out.println(map.toString());
		System.out.println(JSON.toJSONString(map));
		if (map.containsKey("m1")) {
			System.out.println("1:" + map.get("m1"));
		} else if (map.containsKey("m2")) {
			System.out.println("2:" + map.get("m2"));
		} else if (map.containsKey("3")) {
			System.out.println("3:" + map.get("m3"));
		}
	}

	public static String getJson(Object json) {
		return JSON.toJSONString(json);
	}
}
