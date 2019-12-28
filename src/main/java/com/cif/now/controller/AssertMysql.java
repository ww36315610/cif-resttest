package com.cif.now.controller;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.DataProviderMysql;

public class AssertMysql {

	@BeforeMethod
	public void init() {
	}

	@Test(dataProvider = "mysql_data", dataProviderClass = DataProviderMysql.class)
	public void testMethod(JSONObject object) {
		System.out.println(object.toJSONString());
	}
}
