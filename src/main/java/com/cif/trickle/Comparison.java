package com.cif.trickle;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.trickle.channel.DoFilter_Hive;
import com.cif.trickle.channel.DoFilter_Mysql;
import com.cif.utils.fanshe.FieldFilter;
import com.cif.utils.hive.HiveOperation;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

public class Comparison {
	static String driver_mysql = "com.mysql.jdbc.Driver";
	static String url_mysql = "jdbc:mysql://10.10.28.5:3306/wj_test";
	static String user_mysql = "cifuser";
	static String pass_mysql = "cifuser@123";
	MysqlOperation mcbp = MysqlOperation.getInstance(driver_mysql, url_mysql, user_mysql, pass_mysql);
	Connection conn_mysql = mcbp.getConnection();

	private static String driver_hive = "org.apache.hive.jdbc.HiveDriver";// jdbc驱动路径
	private static String url_hive = "jdbc:hive2://172.16.4.31:10000/offline_test";// hive库地址+库名
	private static String user_hive = "";// 用户名
	private static String pass_hive = "";// 密码
	HiveOperation hop = HiveOperation.getInstace(driver_hive, url_hive, user_hive, pass_hive);
	Connection conn_hive = (Connection) hop.getConn();

	@Test(dataProvider = "mysqlCase_tricle")
	public void mysql(JSONObject object) {
		try {
			// 反射得到对应的渠道table[mysql]
			DoFilter_Mysql channel_mysql = FieldFilter.getDoFilterMysql(object);
			// 反射得到对应的渠道table[hive]
			DoFilter_Hive channel_hive = FieldFilter.getDoFilterHive(object);
			// 得到mysql查询结果
			JSONArray array_mysql = channel_mysql.select(FieldFilter.getParamMysql(object), conn_mysql);
			// 得到hive查结果
			JSONArray array_hive = channel_hive.select(FieldFilter.getParamHive(object), conn_hive);
			// 反射得到对应的渠道bean
			List<String> beanList = FieldFilter.getBeanFilter(object);
			// for (Object objectMysql : array_mysql) {
			for (int i = 0; i < array_mysql.size(); i++) {
				JSONObject jsonMysql = JSONObject.parseObject(array_mysql.get(i).toString());
				for (String colmneName : beanList) {
					// Assert.assertEquals(jsonMysql.get(colmneName),
					// jsonObjectMysql.get(colmneName), "Fail!!!!");
					assertBetweenArray(i, colmneName, jsonMysql, array_hive);
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void assertBetweenArray(int i, String columne, JSONObject object, JSONArray array) {
		JSONObject jsonhive = JSONObject.parseObject(array.get(i).toString());
		if (jsonhive.get(columne).equals(object.get(columne))) {
			Assert.assertTrue(true);
		} else {
			Assert.assertEquals(object.get(columne), jsonhive.get(columne), columne + i + "is fail!!!");
		}
	}

	public void assertBetween(String columne, JSONObject object, JSONArray array) {
		for (Object json : array) {
			JSONObject jsonhive = JSONObject.parseObject(json.toString());
			if (jsonhive.get(columne).equals(object.get(columne))) {
				Assert.assertTrue(true);
			}
		}
	}
}
