package com.cif.utils.hive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HiveDaoImp implements HiveDao {

	@Override
	// 增删改、创建表
	public int doHive(String sql, Connection conn) {
		int i = 0;
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			i = pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	// 查询表
	public JSONArray select(String sql, Connection conn) {
		JSONArray jsonArry = new JSONArray();
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			// 获取字段个数
			int col = rs.getMetaData().getColumnCount();
			// 获取字段数据
			ResultSetMetaData data = (ResultSetMetaData) rs.getMetaData();
			while (rs.next()) {
				JSONObject josnObject = new JSONObject();
				for (int i = 1; i <= col; i++) {
					String columnName = data.getColumnName(i);
					String coumn = columnName.split("\\.")[1];
					String value = rs.getString(i).toString();
					josnObject.put(coumn, value);
				}
				jsonArry.add(josnObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonArry;
	}

	public static void main(String[] args) {
		String driverName = "org.apache.hive.jdbc.HiveDriver";// jdbc驱动路径
		String url = "jdbc:hive2://172.16.4.31:10000/offline_test";// hive库地址+库名
		String user = "";// 用户名
		String password = "";// 密码
		String tableName = "dw_mongo_crawler_db_credit_report_info_house_overdue";// hive表名
		String sql = "select * from " + tableName;
		HiveOperation hop = HiveOperation.getInstace(driverName, url, user, password);
		Connection conn = hop.getConn();
		HiveDaoImp hdi = new HiveDaoImp();
		hdi.select(sql, conn);
	}
}
