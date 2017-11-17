package com.cif.restfull_jenkins;

import java.util.List;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.FileOperation;

public class Line_AssertClass_Compare {

	List<String> idcard = FileOperation.readFileByLineString("F:\\idcard2.txt");

	@Test(dataProvider = "mysql_retsfull_data", dataProviderClass = CaseData.class)
	public void method(JSONObject object) {
		
		// String idCardNum = "522101198912184431";
		String idCardNum = "xybf158e78de42ac471557e862467838b6277b8d8f924dd0c4b869b4b41f5848f220160926";
		idcard.forEach(m -> {
			JSONArray compareJsonObject = (JSONArray) Line_HttpResponse.compareParam(object, m).get("tag");
			JSONArray compareJsonPreObject = (JSONArray) Line_HttpResponse.comparerPreParam(object, m).get("tag");
			boolean con =compareJsonObject.equals(compareJsonPreObject);
			System.out.println(m+"["+con+"]");
			
//			Assert.assertTrue(compareJsonObject.equals(compareJsonPreObject));
		});
	}

	// public static void main(String[] args) {
	// JSONObject object = JSONObject.parseObject(jsonParams);
	// ExecutorService executor = Executors.newFixedThreadPool(3);
	// for (int t = 0; t < 2; t++) {
	// executor.execute(() -> {
	// Line_AssertClass_Compare lan = new Line_AssertClass_Compare();
	// lan.method(object);
	// });
	// }
	// }
}
