package com.cif.now;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.utils.resource.ConfigTools;
import com.cif.now.utils.resource.MysqlTools;

public class DataProviderMysql {
	static MysqlTools tools;
	static String driver = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbDriver");
	static String url = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbUrl");
	static String user = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.username");
	static String pass = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.password");
	static String sql = "SELECT * FROM resfull_case_1 WHERE code_1 = 'tag' AND channel ='qz'";
	static Object[][] obj;

	@DataProvider(name = "mysql_data")
	public static Object[][] getData() {
		return getObject(sql);
	}

	public static Object[][] getObject(String sql) {
		List<JSONObject> list = MysqlTools.getInstance(driver, url, user, pass).getDatas(sql);
		obj = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			obj[i][0] = (JSONObject) list.get(i);
		}
		return obj;
	}
}
