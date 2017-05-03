package com.cif.restfull_jenkins;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.ConfigTools;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlDoEasy;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

public class CaseData {
	static String sql = "select * from resfull_case";

	private static List<Map<String, Object>> list;
	private static MysqlOperation mcbp;
	private static Connection conn;
	private static MysqlDao md;
	static String driver;
	static String url;
	static String user;
	static String pass;

	static {
		driver = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbDriver");
		url = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbUrl");
		user = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.username");
		pass = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.password");
		mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		conn = mcbp.getConnection();
		md = new MysqlDaoImp();
	}

	@DataProvider(name = "mysql_retsfull_data")
	public static Object[][] getData() {
		MysqlDoEasy myeasy = new MysqlDoEasy();
		JSONArray jsonArray = myeasy.select(sql, conn);
		Object[][] obj = new Object[jsonArray.size()][1];
		for (int i = 0; i < jsonArray.size(); i++) {
			obj[i][0] = (JSONObject) jsonArray.get(i);
		}
		return obj;
	}
}
