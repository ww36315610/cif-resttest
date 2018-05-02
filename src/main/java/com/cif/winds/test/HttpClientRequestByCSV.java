package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.java.MapCompareTools;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class HttpClientRequestByCSV {

	private Pair<String, List<String>> pair;
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json;charset=UTF-8");
	}

//	static {
//		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
//		TagsRequest tr = new TagsRequest();
//		OAuth2AccessToken token = Oauth.getToken(autoUrl);
//		// 设置header
//		map.put("Accept", "*/*");
//		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
//		map.put("Content-Type", "application/json");
//	}
//    static String filePath = "/Users/apple/Downloads/graylog-search-result-absolute-2018-04-17T00_00_00.000Z-2018-04-17T03_00_00.000Z.csv";
	static String filePath = "/Users/apple/Downloads/graylog-search-result-relative-300.csv";
    static List<String> listKeys = CsvReadTools.getDataFromCSV(filePath);
	String url="";
	int i = 0;
	String json=null;
	// 打开执行开关，并执行对应case

	public void controller(RestfulDao rd) {
		String fileOut = "/Users/apple/Documents/result_1000/pre-error_0420.txt";
		System.out.println("ppp:"+listKeys);
		for (; i < listKeys.size(); i++) {
			final int a= i;
			url = "https://api.puhuifinance.com/cif-utc-rest-pre";
			json = listKeys.get(a).split("###")[1];
			json = returnUTF(json);
			url = url + listKeys.get(a).split("###")[0];
			System.out.println("-------"+url);
			try{
					ThreadPoolUtils.getThreadPoolExecutor().execute(() -> {
						JSONObject jsonResult=null;
						try {
							jsonResult= (JSONObject) rd.getJsonArray(url, map, json).get(0);
						}catch(Exception e){
							String ejson = JSONObject.parseObject(json).getJSONObject("params").toJSONString();
							FileOperation.writeFile(fileOut,"【"+a+"】:"+ejson +"--"+ e.getMessage());
						}

						String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"), SerializerFeature.WriteMapNullValue);
						String slowSql = "【" + a + "】" + jsonRR + "----" + jsonResult.get("castTime");
						System.out.println(slowSql);
					if(Integer.parseInt(jsonResult.getString("castTime"))>3000)
						FileOperation.writeFile(fileOut,slowSql);
					if (jsonResult.getInteger("failCount") > 0) {
						Map<String,Object> map  = Maps.newHashMap();
						map = JSONObject.parseObject(json).getJSONObject("params");
						System.err.println(jsonResult);
					if((jsonResult.getString("failTagReasonMap").contains("doesn't exist"))||(jsonResult.getString("failTagReasonMap").contains("does not exist"))){
						System.err.println("["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
						FileOperation.writeFile(fileOut,"["+a+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
					}else{
						System.out.println("["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
						FileOperation.writeFile(fileOut,"["+a+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
					}

				} else {
//					System.err.println("【" + i + "】" + jsonResult);
				}
				});
			}catch(Exception e){
				System.out.println("111111"+json);
				String ejson = "";
					System.out.println("22222"+JSONObject.parseObject(json));
						ejson = JSONObject.parseObject(json).getJSONObject("params").toJSONString();
					FileOperation.writeFile(fileOut,"【"+a+"】:"+ejson +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
	}
	public String returnUTF(String source){
		if (!java.nio.charset.Charset.forName("utf-8").newEncoder().canEncode(source)) {
			try {
				String dest = new String(source.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error:::::"+e.getMessage());
			}
		}
		return source;
	}

	public static void main(String[] args) throws Exception {
		RestfulDao rd = new RestfullDaoImp();
		HttpClientRequestByCSV ucp = new HttpClientRequestByCSV();
//		ucp.controller(rd);
		for(int i=0;i<9;i++){
			new Thread(new Runnable(){
				public void run(){
//					ucp.controller(rd);
//					synchronized (this){
						ucp.controllerT(rd);
//					}
				}
			}).start();
    }
}


	public void controllerT(RestfulDao rd) {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		/*
		listKeys.forEach(x -> {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					String url = "https://api.puhuifinance.com/cif-utc-rest-pre";
					String json = x.split("###")[1];
//			json = returnUTF(json);
					url = url + x.split("###")[0];
					System.out.println(json);
				}
			});
		});
*/

		try {
			for (int i=0; i < listKeys.size(); i++) {
				final int a = i;


				//System.out.println(a + "]]]-+++++++++++" + json);

				pool.execute(() -> {
					String url = "https://api.puhuifinance.com/cif-utc-rest-pre";
					String json = listKeys.get(a).split("###")[1];
//			json = returnUTF(json);
					url = url + listKeys.get(a).split("###")[0];
				System.out.println(a + "]]]-------------" + json);
				});

			}
		}catch(Exception e){

			}



	}
}
