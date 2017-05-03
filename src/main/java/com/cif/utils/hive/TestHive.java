package com.cif.utils.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestHive {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";// jdbc驱动路径
	private static String url = "jdbc:hive2://172.16.4.31:10000/offline_test";// hive库地址+库名
	private static String user = "";// 用户名
	private static String password = "";// 密码
	private static String sql = "";
	private static ResultSet res;

	@DataProvider(name = "hiveJson")
	public Object[][] getHiveData() {
		String tableName = "offline_test.dw_mongo_crawler_db_credit_report_info_house_overdue";// hive表名
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

	@Test(dataProvider = "hiveJson")
	public void testHiveData(JSONObject jsonObject) {
		Assert.assertEquals(jsonObject.get("house_overdue_currency"), "reb", "Fail!!!!");
		Assert.assertEquals(jsonObject.get("report_no"), "28364", "Fail!!!!");
	}

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConn();
			System.out.println(conn);
			stmt = conn.createStatement();
			String tableName = "offline_test.dw_mongo_crawler_db_credit_report_info_house_overdue";// hive表名
			sql = "select * from " + tableName;
			System.out.println("Running:" + sql);
			res = stmt.executeQuery(sql);
			ResultSetMetaData data = (ResultSetMetaData) res.getMetaData();
			System.out.println("执行 select * query 运行结果:");
			while (res.next()) {
				int i = 1;
				System.out.println(data.getColumnName(i));
				System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t"
						+ res.getString(4));
				i++;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
}