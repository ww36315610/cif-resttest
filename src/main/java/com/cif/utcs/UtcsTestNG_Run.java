package com.cif.utcs;

import org.testng.annotations.Factory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;


/**
 * 执行工厂类，调用@Test方法，获取到mysql得到的预期值
 * @author WJ
 *
 */
public class UtcsTestNG_Run {
	static String httpUrl = "http://192.168.136.31:8898//tags/submit_sql";
	static String httpFilterKey = "idNo";
	static String httpFilterValue = "370102197511012913";

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://10.10.28.5:3306/wj_test";
	static String user = "cifuser";
	static String pass = "cifuser@123";
	static String sql4 = "SELECT * FROM utcs_case limit 2;";
	static String sql5 = "SELECT * FROM utcs_case;";
	static String expecteds;

	@Factory
	public static Object[] factoryMethod() {
		MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		Connection conn = mcbp.getConnection();
		MysqlDao md = new MysqlDaoImp();
		JSONArray jsonArry = md.select(sql4, conn);
		Object[] object = new Object[jsonArry.size()];
		// System.out.println(jsonArry.size());
		int i = 0;
		for (Object json : jsonArry) {
			JSONObject jsonMysql = (JSONObject) json;
			String sql = jsonMysql.get("sql").toString();
			String tag = jsonMysql.get("tag").toString();
			expecteds = jsonMysql.get("expecteds").toString();
			UtcsTesNG_Factory testFactory = new UtcsTesNG_Factory(httpUrl, httpFilterKey, httpFilterValue, sql,
					expecteds, tag);
			object[i] = testFactory;
			i++;
		}
		return object;
	}

}
