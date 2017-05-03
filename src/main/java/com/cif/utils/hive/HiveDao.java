package com.cif.utils.hive;

import java.sql.Connection;

import com.alibaba.fastjson.JSONArray;

public interface HiveDao {
	// 建表、增删改
	public int doHive(String sql, Connection conn);

	// 查询表
	public JSONArray select(String sql, Connection conn);

}
