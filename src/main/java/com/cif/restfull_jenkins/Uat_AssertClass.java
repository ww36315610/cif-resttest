package com.cif.restfull_jenkins;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Uat_AssertClass {
	ExecutorService executor = Executors.newFixedThreadPool(10);

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {

		JSONArray array = Uat_HttpResponse.getResponse(object,
				"xy5d7fd593582e54cdbeaf1b6b02072f471e76b3ef271947443a6992c6d372723620160926");
		for (int t = 0; t < 10; t++) {
			executor.execute(() -> {
				for (int i = 0; i < array.size(); i++) {
					JSONObject json = json = JSONObject.parseObject(array.get(i).toString());
					if (json.toString().contains("beanMap")) {
						if (json.getJSONArray(object.get("code_1").toString()).size() > 0) {
							if (object.get("code_1").toString().equals("tag")) {
								String tag = json.getJSONArray(object.get("code_1").toString()).toString();
								// 打印线程名称
								// System.out.println(Thread.currentThread().getName());
								if (tag.contains("CST")) {
									System.err.println("This is Error!");
								}
							}
						}
					}
				}
			});
		}

		// for (int i = 0; i < array.size(); i++) {
		// json = JSONObject.parseObject(array.get(i).toString());
		// Assert.assertEquals(json.get(object.get("code")),
		// object.get("expecteds"), "返回码不正确！！！！！");
		// if (object.get("code_1").toString().equals("tag")) {
		// Assert.assertTrue(json.getJSONArray(object.get("code_1").toString()).size()
		// >= Integer.parseInt(object
		// .get("expecteds_1").toString()));
		// } else {
		// String expecteds = object.get("expecteds_1").toString();
		// int expNum = Integer.parseInt(expecteds);
		// String actual = json.getString(object.getString("code_1"));
		// int actNum = Integer.parseInt(actual);
		// Assert.assertTrue(expNum <= actNum);
		// }
		// }
	}
}
