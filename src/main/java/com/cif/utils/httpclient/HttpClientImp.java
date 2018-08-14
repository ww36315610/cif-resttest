package com.cif.utils.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.config.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpClientImp {

	public JSONArray getHttp(HttpClient client, String url, Map<String, Object> map) {
		JSONArray jsonArry = null;
		HttpGet get = new HttpGet(url);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			get.setHeader(key, value);
		}
		HttpResponse response;
		try {
			response = client.execute(get);
			jsonArry = getResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonArry;
	}

	// post接口，传递多个String类型参数
	public JSONArray postHttp(HttpClient client, String url, Map<String, Object> map, List<Object> paramKey,
			List<Object> paramValue) {
		JSONArray jsonArry = null;
		HttpPost post = new HttpPost(url);
		//设置超时时间
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setConnectTimeout(20*5000).setConnectionRequestTimeout(20*1000)
//				.setSocketTimeout(20*5000).build();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(20*1).setConnectionRequestTimeout(20*1)
				.setSocketTimeout(20*1).build();
		post.setConfig(requestConfig);
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
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entityA = response.getEntity();
				jsonArry = getResponse(response);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return jsonArry;
	}

	/**
	 * 访问类型是post，根据JSON格式的参数反回结果【JSON格式】 参数HttpClient：定义HttpClient，避免在方法中实例化
	 * 参数url：存放访问的url链接地址 参数map：存取header信息 参数json：存取传递的参数
	 */
	public JSONArray postHttp(HttpClient client, String url, Map<String, Object> map, String json) {
		JSONArray jsonArry = null;
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse  response=null;
		//设置超时时间
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setConnectTimeout(20*5000).setConnectionRequestTimeout(20*1000)
//				.setSocketTimeout(20*5000).build();

//		RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
//		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

//		post.setConfig(requestConfig);
		// for (Map.Entry<String, Object> entry : map.entrySet()) {
		// String key = entry.getKey().toString();
		// String value = entry.getValue().toString();
		// post.setHeader(key, value);
		// System.out.println(value);
		// }
		map.forEach((k, v) -> {
			post.setHeader(k, v.toString());
		});
//		System.out.println("eee"+json);
		StringEntity entiry = new StringEntity(json, "utf-8");
		post.setEntity(entiry);

		try {

			long s = System.currentTimeMillis();
			response = (CloseableHttpResponse) client.execute(post);
			//强制关闭
//			response.close();
//			System.out.println("HTTP POST use: " + (System.currentTimeMillis() - s) + "ms");
			if (response.getStatusLine().getStatusCode() == 200) {
				jsonArry = getResponse(response);
			} else {
				System.out.println("===code====" + response.getStatusLine().getStatusCode());
				return JSONArray.parseArray("[{\"resultMap\":{\"返回code#####\":"+response.getStatusLine().getStatusCode()+"}}]");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e){
			client = new DefaultHttpClient();
		}
		finally {
			post.releaseConnection();
		}
		return jsonArry;
	}

	// 根据resonse的返回结果，生成JSONArray返回【公用代码抽象出来的】
	public static JSONArray getResponse(HttpResponse response) {
		List<String> list = null;
		JSONArray jsonArry = new JSONArray();
		JSONObject object = new JSONObject();
		HttpEntity hEntity = response.getEntity();
		try {
			if (hEntity != null) {
				String respons = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
//				System.out.println("==="+respons);
				try {
					if (respons.startsWith("[")) {
						jsonArry = JSONArray.parseArray(respons);
//						System.out.println(jsonArry);
					}else if(StringUtils.isEmpty(respons)){
						String responsNew = "[{" + respons + "}]";
						jsonArry = JSONArray.parseArray(responsNew);
					} else {
						String responsNew = "[" + respons + "]";
						jsonArry = JSONArray.parseArray(responsNew);
//						System.out.println("---"+jsonArry);
					}
				} catch (Exception e) {
					System.out.println("格式转换从错误，返回json格式不正确！！！" + HttpClientImp.class);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonArry;

	}



	/**
	 * 访问类型是post，根据JSON格式的参数反回结果【JSON格式】 参数HttpClient：定义HttpClient，避免在方法中实例化
	 * 参数url：存放访问的url链接地址 参数map：存取header信息 参数json：存取传递的参数
	 */
	public JSONObject postHttpJSON(HttpClient client, String url, Map<String, Object> map, String json) {
		JSONObject object = null;
		HttpPost post = new HttpPost(url);
		//设置超时时间
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setConnectTimeout(20*5000).setConnectionRequestTimeout(20*1000)
//				.setSocketTimeout(20*5000).build();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(20*1).setConnectionRequestTimeout(20*1)
				.setSocketTimeout(20*1).build();
		post.setConfig(requestConfig);
		map.forEach((k, v) -> {
			post.setHeader(k, v.toString());
		});
		StringEntity entiry = new StringEntity(json, "utf-8");
		post.setEntity(entiry);
		try {

			long s = System.currentTimeMillis();
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				object = getResponseJSON(response);
			} else {
				System.out.println("===code====" + response.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return object;
	}

	// 根据resonse的返回结果，生成JSONArray返回【公用代码抽象出来的】
	public static JSONObject getResponseJSON(HttpResponse response) {
		List<String> list = null;
		JSONArray jsonArry = new JSONArray();
		JSONObject object = new JSONObject();
		HttpEntity hEntity = response.getEntity();
		try {
			if (hEntity != null) {
				String respons = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
				try {
					//object = JSONObject.parseObject(respons, Feature.IgnoreNotMatch);
					object = JSONObject.parseObject(respons);
				} catch (Exception e) {
					System.out.println("格式转换从错误，返回json格式不正确！！！" + HttpClientImp.class);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;

	}
}
