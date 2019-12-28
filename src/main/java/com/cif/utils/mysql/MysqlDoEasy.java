package com.cif.utils.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;

public class MysqlDoEasy {

	// 操作mysql
	public int doSql(String sql, Connection conn) {
		int i = 0;
		try {
			PreparedStatement pre = conn.prepareStatement(sql);
			i = pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	// 获取mysql数据
	public JSONArray select(String sql, Connection conn) {
		JSONArray jsonArry = new JSONArray();
		PreparedStatement pre = null;
		ResultSet rSet = null;
		try {
			pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			int col = rs.getMetaData().getColumnCount();
			ResultSetMetaData data = (ResultSetMetaData) rs.getMetaData();
			while (rs.next()) {
				JSONObject josnObject = new JSONObject();
				for (int i = 1; i <= col; i++) {
					String columnName = data.getColumnName(i);
					String value = rs.getString(i).toString();
					josnObject.put(columnName, value);
				}
				jsonArry.add(josnObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonArry;
	}
}
