package com.cif.trickle.channel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;

public class DoFilter_Hive {
	// TODO:初始化

	// TODO:执行语句

	// TODO:返回值 所有mysql的字段name+value
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
}
