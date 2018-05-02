package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientRequestByCSV_LIunx {

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



	String url="";
	int i = 1;
	String json=null;
	// 打开执行开关，并执行对应case
	public void controller(RestfulDao rd,String fileOut,List<String> listKeys) {
//		String fileOut = "/Users/apple/Documents/result_1000/pre-error_0420.txt";
		for (; i <= listKeys.size(); i++) {
			final int a= i;
			url = "https://api.puhuifinance.com/cif-utc-rest-pre";
			json = listKeys.get(a).split("###")[1];
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
					String ejson = JSONObject.parseObject(json).getJSONObject("params").toJSONString();
					FileOperation.writeFile(fileOut,"【"+a+"】:"+ejson +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
	}


	public static void main(String[] args) throws Exception {
		RestfulDao rd = new RestfullDaoImp();
		HttpClientRequestByCSV_LIunx ucp = new HttpClientRequestByCSV_LIunx();
//		ucp.controller(rd);

		String csvPath = System.getProperty("csv");
		String fileOut = System.getProperty("file");
		List<String> listKeys = CsvReadTools.getDataFromCSV(csvPath);
		String threadNum = args.length>0?args[0]:System.getProperty("threadNum");

		for(int i=0;i<Integer.parseInt(threadNum);i++){
			new Thread(new Runnable(){
				public void run(){
					ucp.controller(rd,fileOut,listKeys);
				}
			}).start();
		}
	}
}
