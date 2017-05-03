package com.cif.utcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.httpclient.HttpClientInterImp;

/**
 * 此类获取HrrpClient返回值  比对mysql结果
 * @author WJ
 *
 */
public class UtcsTesNG_Factory implements ITest {

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
	static JSONArray jsonHttp;

	static {
		paramKey = new ArrayList<Object>();
		paramKey.add("code");
		paramKey.add("filter");
		paramKey.add("dbName");
	}

	public UtcsTesNG_Factory(String httpUrl, String httpFilterKey, String httpFilterValue, String sql,
			String expecteds, String tag) {
		this.httpUrl = httpUrl;
		this.httpFilterKey = httpFilterKey;
		this.httpFilterValue = httpFilterValue;
		this.sql = sql;
		this.tag = tag;
		this.expecteds = expecteds;
	}

	@BeforeMethod
	public void getJsonArray() {
		paramValue = new ArrayList<Object>();
		String filter = httpFilterKey + "='" + httpFilterValue + "'";
		paramValue.add(sql);
		paramValue.add(filter);
		paramValue.add("dbName");
		jsonHttp = hcdi.postStringA(client, httpUrl, map, paramKey, paramValue);
	}

	@Test
	public void assertTest() {
		JSONObject jsonActual = JSONObject.parseObject(jsonHttp.getString(0).toString());
		String actual = jsonActual.getString(tag);
		failString = "【" + tag + "】：Tag值有误，预期是【" + expecteds + "】实际值【" + actual + "】";
		Assert.assertEquals(actual, expecteds, failString);
	}

	public String getTestName() {
		return "";
	}
}
