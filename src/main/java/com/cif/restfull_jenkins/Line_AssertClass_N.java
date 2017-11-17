package com.cif.restfull_jenkins;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Line_AssertClass_N {
	ExecutorService executor = Executors.newFixedThreadPool(3);
	static String jsonParams = "{\"params\":{\"idCardNum\":\"idCardNum_change\",\"type\":\"mobile_info.voices\"},\"url\":\"api/v1/getOneBasicColumn\"}";

	// @Test(dataProvider = "mysql_retsfull_data", dataProviderClass =
	// CaseData.class)
	public void method(JSONObject object) {

		try {
			JSONArray array = Line_HttpResponse.getResponse(object, "510403199204170311");
			if (array.size() > 0) {
				JSONObject json = JSONObject.parseObject(array.get(0).toString());
				if (json.toString().contains("object")) {
					JSONArray tag = json.getJSONArray("tag");
					JSONObject jb = JSONObject.parseObject(tag.get(0).toString());
					// 打印线程名称
					// System.out.println(Thread.currentThread().getName());
					if (jb.get("object").toString().contains("CST")) {
						System.out.println(jb.get("object").toString());
						System.err.println("This is Error!");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JSONObject object = JSONObject.parseObject(jsonParams);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int t = 0; t < 2; t++) {
			executor.execute(() -> {
				Line_AssertClass_N lan = new Line_AssertClass_N();
				lan.method(object);
			});
		}
	}
}
