package com.cif.now.utils.resource;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cif.utils.mysql.MysqlOperation;
import com.google.common.collect.Lists;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class MysqlTools {
	private static String driver;
	private static String url;
	private static String user;
	private static String pass;
	public static Connection conn;
	public static PreparedStatement pre;
	public static ResultSet rs;
	private static MysqlTools mbp = null;
	private static int i = 0;

	private MysqlTools(String driver, String url, String user, String pass) {
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
			// 链接数据库
			conn = (Connection) DriverManager.getConnection(url, user, pass);
		} catch (SQLException a) {
			System.err.println("sql exception:" + a.getMessage());
		}
	}

	public static MysqlTools getInstance(String driver, String url, String user, String pass) {
		if (mbp == null) {
			// 给类加锁 防止线程并发
			synchronized (MysqlOperation.class) {
				if (mbp == null) {
					mbp = new MysqlTools(driver, url, user, pass);
					System.out.println(mbp.getClass().getName());
				}
			}
		}
		return mbp;
	}

	// 获得连接
	public Connection getConnection() {
		return conn;
	}

	// 关闭连接
	public void closeConnection(ResultSet rs, Statement statement, Connection con) {
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

	public int insert(String sql) {
		try {
			pre = conn.prepareStatement(sql);
			i = pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int batchInsert(List<String> sqls) {
		sqls.forEach(s -> {
			try {
				pre = conn.prepareStatement(s);
				i = pre.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return i;
	}

	public static List<JSONObject> getDatas(String sql) {
		List<JSONObject> list = Lists.newArrayList();
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
					// System.out.println(columnName);
					// System.out.println(value);
					josnObject.put(columnName, value);
				}
				list.add(josnObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public static PreparedStatement getPre() {
		return pre;
	}

	public static void setPre(PreparedStatement pre) {
		MysqlTools.pre = pre;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static void setRs(ResultSet rs) {
		MysqlTools.rs = rs;
	}

	public static MysqlTools getMbp() {
		return mbp;
	}

	public static void setMbp(MysqlTools mbp) {
		MysqlTools.mbp = mbp;
	}

	public static int getI() {
		return i;
	}

	public static void setI(int i) {
		MysqlTools.i = i;
	}
}