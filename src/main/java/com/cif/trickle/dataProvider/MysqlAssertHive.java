package com.cif.trickle.dataProvider;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.fanshe.FieldFilter;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

public class MysqlAssertHive {
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://10.10.28.5:3306/wj_test";
	static String user = "cifuser";
	static String pass = "cifuser@123";
	static String sql4 = "SELECT * FROM utcs_case;";
	MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
	Connection conn = mcbp.getConnection();
	MysqlDao md = new MysqlDaoImp();
	JSONArray jsonArry = md.select(sql4, conn);

	int i = 0;
	@Test(dataProvider = "hiveJson", dataProviderClass = HiveDataProvider.class)
	public void testHiveData(JSONObject jsonObject) {
		JSONObject jsonObjectMysql = (JSONObject) jsonArry.get(i);
		List<String> list;
		try {
			// TODO:修改成从数据库case中得到bean类【channel】
			list = FieldFilter.getBeanName("com.cif.trickle.bean.TestBean");

			for (String colmneName : list) {
				Assert.assertEquals(jsonObject.get(colmneName), jsonObjectMysql.get(colmneName), "Fail!!!!");
			}
			i++;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
