package com.cif.restfull_jenkins;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AssertClass {

	@BeforeTest
	private void getApplyNo() {

	}

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {
		JSONArray array = HttpResponse.getResponse(object, "xy691fc4b8c804544369e6146d4314282e20160926");
		JSONObject json = null;
		for (int i = 0; i < array.size(); i++) {
			json = JSONObject.parseObject(array.get(i).toString());
			Assert.assertEquals(json.get(object.get("code")), object.get("expecteds"), "返回码不正确！！！！！");
			Assert.assertTrue(json.getJSONArray(object.get("code_1").toString()).size() >= Integer.parseInt(object.get(
					"expecteds_1").toString()));
		}
	}
}
