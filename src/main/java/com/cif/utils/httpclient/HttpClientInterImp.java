package com.cif.utils.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpClientInterImp implements HttpClientInter {

	/**
	 * 方法：getString：访问类型是GET，根据字符串参数反回结果【JSON格式】
	 * 参数HttpClient：定义HttpClient，避免在方法中实例化 参数url：存放访问的url链接地址 参数Map：存取header信息
	 */
	public JSONObject getString(HttpClient client, String url, Map<String, Object> map) {
		JSONObject jsonResult = null;
		HttpGet get = new HttpGet(url);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			get.setHeader(key, value);
		}
		HttpResponse response;
		try {
			response = client.execute(get);
			jsonResult = getResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	/**
	 * 方法：postString：访问类型是post，根据字符串参数反回结果【JSON格式】
	 * 参数HttpClient：定义HttpClient，避免在方法中实例化 参数url：存放访问的url链接地址 参数Map：存取header信息
	 * 参数paramKey：存放接口代码中springboot的注解值[@后面的值] 参数paramValue：存储接口中实际的参数值
	 */
	public JSONObject postString(HttpClient client, String url, Map<String, Object> map, List<Object> paramKey,
			List<Object> paramValue) {
		JSONObject jsonResult = null;
		HttpPost post = new HttpPost(url);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			post.setHeader(key, value);
		}
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (int i = 0; i < paramKey.size(); i++) {
			list.add(new BasicNameValuePair(paramKey.get(i).toString(), paramValue.get(i).toString()));
		}
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(list, "utf-8");
			post.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(post);
			jsonResult = getResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	/**
	 * 方法：postJson：访问类型是post，根据JSON格式的参数反回结果【JSON格式】
	 * 参数HttpClient：定义HttpClient，避免在方法中实例化 参数url：存放访问的url链接地址 参数map：存取header信息
	 * 参数json：存取传递的参数
	 */
	public JSONObject postJson(HttpClient client, String url, Map<String, Object> map, String json) {
		JSONObject jsonResult = null;
		HttpPost post = new HttpPost(url);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			post.setHeader(key, value);
		}
		StringEntity entiry = new StringEntity(json, "utf-8");
		post.setEntity(entiry);
		try {
			HttpResponse response = client.execute(post);
			jsonResult = getResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	public JSONArray postStringA(HttpClient client, String url, Map<String, Object> map, List<Object> paramKey,
			List<Object> paramValue) {
		JSONArray jsonA = null;
		HttpPost post = new HttpPost(url);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			post.setHeader(key, value);
		}
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (int i = 0; i < paramKey.size(); i++) {
			list.add(new BasicNameValuePair(paramKey.get(i).toString(), paramValue.get(i).toString()));
		}
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(list, "utf-8");
			post.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(post);
			HttpEntity entityA = response.getEntity();
			jsonA = getResponseA(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonA;
	}

	// 根据resonse的返回结果，生成JSONArray返回【公用代码抽象出来的】
	public static JSONArray getResponseA(HttpResponse response) {
		List<String> list = null;
		JSONArray jsonA = new JSONArray();
		HttpEntity hEntity = response.getEntity();
		try {
			if (hEntity != null) {
				String respons = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
				try {
					jsonA = JSONArray.parseArray(respons);
				} catch (Exception e) {
					System.out.println("返回接json格式不正确！！！");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonA;

	}

	// 根据resonse的返回结果，生成json返回【公用代码抽象出来的】
	public static JSONObject getResponse(HttpResponse response) {
		JSONObject jsonResult = null;
		HttpEntity hEntity = response.getEntity();
		try {
			if (hEntity != null) {
				String respons = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
				try {
					jsonResult = JSONObject.parseObject(respons);
				} catch (Exception e) {
					System.out.println("返回接json格式不正确！！！");
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
}