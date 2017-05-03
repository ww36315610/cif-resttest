package com.cif.trickle;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

/**
 * 查询case放入DataProvider
 * 
 * @author cnbjpuhui-5051a
 *
 */
public class TrickleCase {
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://10.10.28.5:3306/wj_test";
	static String user = "cifuser";
	static String pass = "cifuser@123";
	static String sql4 = "SELECT * FROM trickle_case;";
	MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
	Connection conn = mcbp.getConnection();
	MysqlDao md = new MysqlDaoImp();

	@DataProvider(name = "mysqlCase_tricle")
	public Object[][] getData() {
		JSONArray jsonArray = md.select(sql4, conn);
		Object[][] obj = new Object[jsonArray.size()][1];
		for (int i = 0; i < jsonArray.size(); i++) {
			obj[i][0] = (JSONObject) jsonArray.get(i);
		}
		return obj;
	}
}
