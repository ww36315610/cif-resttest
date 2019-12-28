package com.cif.utils.mysql;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.Connection;

public interface MysqlDao {
	public int createTable(String sql, Connection conn) throws SQLException;

	public int insert(String sql, Connection conn);

	public int delete(String sql, Connection conn);

	public int update(String sql, Connection conn);

	public JSONArray select(String sql, Connection conn);

}