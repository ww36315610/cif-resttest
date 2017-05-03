package com.cif.utcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.HttpClientInterImp;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

public class UtcsController {
	static String httpUrl = "http://192.168.136.31:8898//tags/submit_sql";
	static String httpFilterKey = "idNo";
	static String httpFilterValue = "370102197511012913";
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://10.10.28.5:3306/wj_test";
	static String user = "cifuser";
	static String pass = "cifuser@123";
	static String sql4 = "SELECT * FROM utcs_case limit 2;";
	static String actual;
	static String expecteds;
	static String failString;

	public static void main(String[] args) {
		// getJson();
		MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		Connection conn = mcbp.getConnection();
		MysqlDao md = new MysqlDaoImp();
		JSONArray jsonArry = md.select(sql4, conn);
		System.out.println(jsonArry);
	}

	@Test
	public static void getJson() {
		MysqlOperation mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		Connection conn = mcbp.getConnection();
		MysqlDao md = new MysqlDaoImp();
		JSONArray jsonArry = md.select(sql4, conn);
		for (Object json : jsonArry) {
			JSONObject jsonMysql = (JSONObject) json;
			JSONArray jsonHttp = UTCSJSON.getJsonArray(httpUrl, httpFilterKey, httpFilterValue, jsonMysql.get("sql")
					.toString());
			JSONObject jsonActual = JSONObject.parseObject(jsonHttp.get(0).toString());
			String tag = jsonMysql.get("tag").toString();
			actual = jsonActual.getString(tag);
			expecteds = jsonMysql.get("expecteds").toString();
			failString = "【" + tag + "】：Tag值有误，预期是【" + expecteds + "】实际值【" + actual + "】";
			System.out.println(failString);
			Assert.assertEquals(actual, expecteds, failString);
		}
	}
}

// 调用utcs接口查询结果
class UTCSJSON {
	static List<Object> paramKey;
	static List<Object> paramValue;
	static HttpClientInterImp hcdi = new HttpClientInterImp();
	static HttpClient client = new DefaultHttpClient();
	static Map<String, Object> map = new HashMap<String, Object>();
	static {
		paramKey = new ArrayList<Object>();
		paramKey.add("code");
		paramKey.add("filter");
		paramKey.add("dbName");
	}

	public static JSONArray getJsonArray(String url, String filterKey, String filterValue, String sql) {
		paramValue = new ArrayList<Object>();
		String filter = filterKey + "='" + filterValue + "'";
		paramValue.add(sql);
		paramValue.add(filter);
		paramValue.add("dbName");
		return hcdi.postStringA(client, url, map, paramKey, paramValue);
	}
}

class TestFactory {
	private String httpUrl;
	private String httpFilterKey;
	private String httpFilterValue;
	private String sql;
	private String failString;
	private String expecteds;
	private String tag;
	static List<Object> paramKey;
	static List<Object> paramValue;
	static HttpClientInterImp hcdi = new HttpClientInterImp();
	static HttpClient client = new DefaultHttpClient();
	static Map<String, Object> map = new HashMap<String, Object>();

	static {
		paramKey = new ArrayList<Object>();
		paramKey.add("code");
		paramKey.add("filter");
		paramKey.add("dbName");
	}

	public TestFactory(String httpUrl, String httpFilterKey, String httpFilterValue, String sql, String failString,
			String expecteds, String tag) {
		this.httpUrl = httpUrl;
		this.httpFilterKey = httpFilterKey;
		this.httpFilterValue = httpFilterValue;
		this.sql = sql;
		this.tag = tag;
		this.failString = failString;
		this.expecteds = expecteds;
	}

	@Test
	public void assertTest() {
		JSONArray jsonHttp = TestFactory.getJsonArray(httpUrl, httpFilterKey, httpFilterValue, sql);
		JSONObject jsonActual = JSONObject.parseObject(jsonHttp.getString(0).toString());
		String actual = jsonActual.getString(tag);
		failString = "【" + tag + "】：Tag值有误，预期是【" + expecteds + "】实际值【" + actual + "】";
		System.out.println(failString);
		Assert.assertEquals(actual, expecteds, failString);
	}

	public static JSONArray getJsonArray(String url, String filterKey, String filterValue, String sql) {
		paramValue = new ArrayList<Object>();
		String filter = filterKey + "='" + filterValue + "'";
		paramValue.add(sql);
		paramValue.add(filter);
		paramValue.add("dbName");
		return hcdi.postStringA(client, url, map, paramKey, paramValue);
	}

}