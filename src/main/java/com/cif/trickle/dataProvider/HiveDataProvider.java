package com.cif.trickle.dataProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.hive.HiveDaoImp;
import com.cif.utils.hive.HiveOperation;

public class HiveDataProvider {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";// jdbc驱动路径
	private static String url = "jdbc:hive2://172.16.4.31:10000/offline_test";// hive库地址+库名
	private static String user = "";// 用户名
	private static String password = "";// 密码
	private static String sql = "";
	private static ResultSet res;

	@DataProvider(name = "hiveJson")
	public Object[][] getHiveData() {
		// TODO:增加参数化
		String tableName = "dw_mongo_crawler_db_credit_report_info_house_overdue";// hive表名
		String sql = "select * from " + tableName;
		HiveOperation hop = HiveOperation.getInstace(driverName, url, user, password);
		Connection conn = hop.getConn();
		HiveDaoImp hdi = new HiveDaoImp();
		JSONArray jsonArray = hdi.select(sql, conn);
		Object[][] obj = new Object[jsonArray.size()][1];
		for (int i = 0; i < jsonArray.size(); i++) {
			obj[i][0] = (JSONObject) jsonArray.get(i);
		}
		return obj;
	}

	@DataProvider(name = "hiveJson")
	public Object[][] getHiveData(String sql) {
		HiveOperation hop = HiveOperation.getInstace(driverName, url, user, password);
		Connection conn = hop.getConn();
		HiveDaoImp hdi = new HiveDaoImp();
		JSONArray jsonArray = hdi.select(sql, conn);
		Object[][] obj = new Object[jsonArray.size()][1];
		for (int i = 0; i < jsonArray.size(); i++) {
			obj[i][0] = (JSONObject) jsonArray.get(i);
		}
		return obj;
	}

	private static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
}