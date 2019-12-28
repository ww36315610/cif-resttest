package com.finup.nbsp.test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JdbcUtils {
	void getSouce() {
		try {
			// 加载数据库驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			// 创建数据连接对象
			String url = "jdbc:mysql://10.10.28.5:3306/wj_test?user=cifuser&password=cifuser@123";
			Connection conn = (Connection) DriverManager.getConnection(url);
			// 创建Statement对象[Statement 类的主要是用于执行静态 SQL 语句并返回它所生成结果的对象]
			Statement statementMysql = (Statement) conn.createStatement();

			// execuUpdate更新【增删改】
			int i = statementMysql.executeUpdate("insert into test values('aa','bb')");
			// executeQuery查询
			ResultSet result = statementMysql.executeQuery("select * from test");

			// 关闭数据库链接
			if (result != null) {
				result.close();
			}
			if (statementMysql != null) {
				statementMysql.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
