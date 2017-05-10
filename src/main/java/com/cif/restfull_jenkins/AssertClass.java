package com.cif.restfull_jenkins;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.restfull_jenkins.filed.ReturnClass;
import com.cif.utils.jiami.JiaMi;
import com.cif.utils.mongo.MongoStoreEnum.KaNiu;
import com.cif.utils.mongo.MongoStoreEnum.QianZhan;

public class AssertClass {
	String idNoJiaMi;

	@BeforeTest
	public void befor() {
		MogoData min = new MogoData();
		min.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		min.doMongo("mongo_kn", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道-----------------------------\n");
		System.out.println(MogoData.applyNo);
		long aa = MogoData.applyNo;
		String idNo = String.valueOf(aa + 2);
		idNoJiaMi = JiaMi.getAes(idNo);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {
		try {
			HttpResponse htr = (HttpResponse) ReturnClass.getHttpClass(object);
			JSONArray array = htr.getResponse(object, idNoJiaMi);
			JSONObject json = null;
			for (int i = 0; i < array.size(); i++) {
				json = JSONObject.parseObject(array.get(i).toString());
				Assert.assertEquals(json.get(object.get("code")), object.get("expecteds"), "返回码不正确！！！！！");
				if (object.get("code_1").toString().equals("tag")) {
					Assert.assertTrue(
							json.getJSONArray(object.get("code_1").toString()).size() >= Integer.parseInt(object.get(
									"expecteds_1").toString()), object.getString("caseID"));
				} else {
					String expecteds = object.get("expecteds_1").toString();
					int expNum = Integer.parseInt(expecteds);
					String actual = json.getString(object.getString("code_1"));
					int actNum = Integer.parseInt(actual);
					Assert.assertTrue(expNum <= actNum, object.getString("caseID"));
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
