package com.cif.now.utils.resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;

public class HttpClientTools {

	/**
	 * 访问类型是post，根据JSON格式的参数反回结果【JSON格式】 参数url：存放访问的url链接地址 参数map：存取header信息
	 * 参数json：存取传递的参数
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String postHttp(String url, Map<String, Object> header, String json) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		header.forEach((k, v) -> {
			httpPost.setHeader(k, v.toString());
		});
		httpPost.setEntity(new StringEntity(json, "utf-8"));
		int code = 0;
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if ((code = httpResponse.getStatusLine().getStatusCode()) == 200) {
				HttpEntity hEntity = httpResponse.getEntity();
				if (hEntity != null) {
					return EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return "code is " + code + "…………";
	}

	// post接口，传递String类型参数
	public static String postHttp(String url, Map<String, Object> map, List<Object> paramKey, List<Object> paramValue)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		map.forEach((k, v) -> {
			httpPost.setHeader(k, v.toString());
		});
		List<NameValuePair> list = Lists.newArrayList();
		for (int i = 0; i < paramKey.size(); i++) {
			list.add(new BasicNameValuePair(paramKey.get(i).toString(), paramValue.get(i).toString()));
		}
		// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
		// "utf-8");
		httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entityPost = response.getEntity();
			String result = EntityUtils.toString(entityPost, EntityUtils.getContentCharSet(entityPost));
			httpPost.releaseConnection();
			return result;
		}
		httpPost.releaseConnection();
		return "";
	}
}