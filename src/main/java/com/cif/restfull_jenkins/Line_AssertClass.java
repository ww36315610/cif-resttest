package com.cif.restfull_jenkins;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Line_AssertClass {

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {

		JSONArray array = Line_HttpResponse.getResponse(object,
				"xye885eb4f4ead67a14a08a1267f3587a58c362ee47435854b8ddff3ddcd6f9be820160926");
		/**
		 * qz：
		 * xy90c66d9cf6ef5ff736c08a6ffe53f403221d12ca2beb6df5276f6a70d222fbc620160926
		 * redhare:
		 * xye885eb4f4ead67a14a08a1267f3587a58c362ee47435854b8ddff3ddcd6f9be820160926
		 * nirvana:
		 * xy90c66d9cf6ef5ff736c08a6ffe53f403221d12ca2beb6df5276f6a70d222fbc620160926
		 * 
		 */

		JSONObject json = null;
		for (int i = 0; i < array.size(); i++) {
			json = JSONObject.parseObject(array.get(i).toString());
			Assert.assertEquals(json.get(object.get("code")), object.get("expecteds"), "返回码不正确！！！！！");
			if (object.get("code_1").toString().equals("tag")) {
				Assert.assertTrue(json.getJSONArray(object.get("code_1").toString()).size() >= Integer.parseInt(object
						.get("expecteds_1").toString()));
			} else {
				String expecteds = object.get("expecteds_1").toString();
				int expNum = Integer.parseInt(expecteds);
				String actual = json.getString(object.getString("code_1"));
				int actNum = Integer.parseInt(actual);
				Assert.assertTrue(expNum <= actNum);
			}
		}
	}
}
