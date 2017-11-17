package com.cif.now.mongo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.DataProviderMysql;
import com.cif.utils.file.FileOperation;

public class Assert_Compare {
	List<String> idcard = FileOperation.readFileByLineString("F:\\idcard2.txt");

	@Test(dataProvider = "mysql_data", dataProviderClass = DataProviderMysql.class)
	public void method(JSONObject object) {
		String idCardNum = "xybf158e78de42ac471557e862467838b6277b8d8f924dd0c4b869b4b41f5848f220160926";
		idcard.forEach(m -> {
			JSONObject obj = L_HttpResponse.compareParam(object, m);
			JSONObject objPre = L_HttpResponse.comparerPreParam(object, m);
			if (StringUtils.isBlank(obj.get("tag").toString())) {
				System.err.println("111111:" + obj);
				System.err.println("222222:" + objPre);
			} else if (StringUtils.isBlank(objPre.get("tag").toString())) {
				System.err.println("444444:" + objPre);
				System.err.println("333333:" + obj);
			
			}
			if (!(StringUtils.isBlank(obj.get("tag").toString()) && StringUtils.isBlank(objPre.get("tag").toString()))) {
				Assert.assertTrue(obj.get("tag").equals(objPre.get("tag")));
			} else {
				System.out.println("返回结果" + obj);
				System.out.println("PRE返回结果" + objPre);
				Assert.assertTrue(false);
			}
		});
	}

	@Test(dataProvider = "mysql_data", dataProviderClass = DataProviderMysql.class)
	public void method1(JSONObject object) {
		for (int i = 0; i < 3; i++) {
			System.out.println("【" + i + "】" + object.get("params").toString());

			// Assert.assertTrue(i < 2);
			Assert.assertTrue(i < 3);
		}
	}
}
