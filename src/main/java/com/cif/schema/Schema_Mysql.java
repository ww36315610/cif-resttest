package com.cif.schema;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

public class Schema_Mysql {
	private static JSONArray jsonArray;
	private static String channelCode;

	public Schema_Mysql(String channelCode) {
		this.channelCode = channelCode;
	}

	// 得到对应的查询数据
	public JSONArray sqlJsonArray(Connection conn, String table_name) {
		MysqlDao md = new MysqlDaoImp();
		jsonArray = md.select(getSql(table_name), conn);
		return jsonArray;
	}

	// 构建sql语句
	private String getSql(String table_name) {
		String sql = "SELECT colType,name FROM table_column_info WHERE table_id IN (SELECT table_id FROM table_info WHERE table_hbase_name ='"
				+ table_name + "' AND  db_id LIKE '" + channelCode + "');";
		System.out.println(sql);
		return sql;
	}

	public static void main(String[] args) {
		String table_name = "customer_basic";
		Schema_Mysql sm = new Schema_Mysql("400%");
		String sql = sm.getSql(table_name);
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.10.28.5:3306/cif_utcs";
		String user = "cifuser";
		String pass = "cifuser@123";
		MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		Connection conn = mcbp.getConnection();
		System.out.println(sm.sqlJsonArray(conn, table_name));
	}
}
