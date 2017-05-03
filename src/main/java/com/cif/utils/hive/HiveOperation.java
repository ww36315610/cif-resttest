package com.cif.utils.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HiveOperation {
	private String driver;
	private String url;
	private String user;
	private String pass;
	private Connection conn;

	private static HiveOperation hop = null;

	private HiveOperation(String driver, String url, String user, String pass) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		try {
			// 注册数据库驱动
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println("class not found:" + e.getMessage());
		}
		try {
			conn = (Connection) DriverManager.getConnection(url, user, pass);
		} catch (SQLException a) {
			System.err.println("sql exception:" + a.getMessage());
		}
	}

	public static HiveOperation getInstace(String driver, String url, String user, String pass) {
		if (hop == null) {
			synchronized (HiveOperation.class) {
				if (hop == null) {
					hop = new HiveOperation(driver, url, user, pass);
				}
			}
		}
		return hop;
	}

	// 关闭连接
	public void closeConnection(ResultSet rs, PreparedStatement statement, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
