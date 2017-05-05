package com.cif.restfull_jenkins;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.restfull_jenkins.filed.ReturnClass;

public class AssertClass {

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {
		try {
			HttpResponse htr = (HttpResponse) ReturnClass.getHttpClass(object);
			// ALL:     xy2323bcac34a11a81934507afe12e9a2820160926
			//QZ:       xy691fc4b8c804544369e6146d4314282e20160926
			JSONArray array = htr.getResponse(object, " xy2323bcac34a11a81934507afe12e9a2820160926");
			JSONObject json = null;
			for (int i = 0; i < array.size(); i++) {
				json = JSONObject.parseObject(array.get(i).toString());
				Assert.assertEquals(json.get(object.get("code")), object.get("expecteds"), "返回码不正确！！！！！");
				if (object.get("code_1").toString().equals("tag")) {
					Assert.assertTrue(json.getJSONArray(object.get("code_1").toString()).size() >= Integer
							.parseInt(object.get("expecteds_1").toString()));
				} else {
					String expecteds = object.get("expecteds_1").toString();
					int expNum = Integer.parseInt(expecteds);
					String actual = json.getString(object.getString("code_1"));
					int actNum = Integer.parseInt(actual);
					System.out.println("[exp:" + expNum + "]---[act:" + actNum + "]---[param:"
							+ object.getString("params") + "]");
					Assert.assertTrue(expNum <= actNum);
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
