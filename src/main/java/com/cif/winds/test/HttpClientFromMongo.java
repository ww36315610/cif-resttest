package com.cif.winds.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.java.MapCompareTools;
import com.cif.utils.java.Oauth;
import com.cif.utils.mongo.MongoDao;
import com.cif.utils.mongo.MongoOperation;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class HttpClientFromMongo {

	private static final String switchDocker = PropersTools.getValue("switch");
	private static final String method = PropersTools.getValue("method");
	private Pair<String, List<String>> pair;
	private static Map<String, Object> header;
	private static String address;
	private RestfulDao rd;
	private SaveMongoDB smd;
	static DB db;
	static DBCollection dbConn;
	static{
		header = headerPut();
	}

	public HttpClientFromMongo(){
		rd = new RestfullDaoImp();
		db = MongoOperation.getMongoDatabase("mongo_feather_utc_rest");
		dbConn = MongoOperation.mongoDBConn(method);
	}

	public static void main(String[] args) {
		String fileOut = "/Users/apple/Documents/result_1000/mongo-pre-compare.txt";
		String fileCSV = "/Users/apple/Downloads/graylog-search-result-absolute-2018-04-17T00_00_00.000Z-2018-04-17T03_00_00.000Z.csv";
		HttpClientFromMongo hcrd = new HttpClientFromMongo();
//		hcrd.controller();
//		hcrd.controllerT();
//		hcrd.caseMake();
		String before = "cif-utc-rest-pre";
		String after="cif-utc-rest";


		//按照jdbc.properties的case走
//		List<String> list = PropersTools.getKeys();
		//按照读取csv的case走
//		List<String> listCSV = CsvReadTools.getCaseFromCSV(fileCSV);

		for(int i=0;i<1;i++){
			new Thread(new Runnable(){
				public void run(){
					//	比对
//					hcrd.controllerTCompareList(listCSV,before,after,fileOut);
					//写入mongo-response-request
//				    hcrd.controllerTSaveMongo(listCSV,fileOut);
					hcrd.controllerTCompareList(findMongo(),fileOut);
				}
			}).start();
		}

		findMongo();
	}


	/**
	 * 启动线程池来执行case==list来自mongo
	 * 返回map比较
	 */
	public void controllerTCompareList(List<String> list,String fileOut) {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		try{
		list.forEach(l->{
			pool.execute(()->{
				String url = address + method;
				JSONObject jsonMongo = JSONObject.parseObject(l.toString());
				String json = jsonMongo.getString("request");
				String id = jsonMongo.getString("_id");
				JSONObject responseMongo = JSONObject.parseObject(jsonMongo.getString("response"));
				int castTimeMongo = Integer.parseInt(jsonMongo.getString("castTime"));
//				System.out.println(url	+json);
				JSONObject responsePre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
//				System.out.println("mmmmmm"+jsonMongo);
//				System.out.println("pppppp"+responsePre);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int castTimePre = Integer.parseInt(responsePre.getString("castTime"));
				if(!(responseMongo.toString().contains("code#####")||responsePre.getString("resultMap").contains("code#####"))) {
					String resultCompare = compareResultNoResultMap(responseMongo,responsePre)=="Same map"?"True【"+method+":"+id+"】":"False【"+method+":"+id+"】";
					if(resultCompare.contains("False")) {
						System.err.println(resultCompare);
						System.out.println("mmmmmm"+jsonMongo.getString("response"));
						System.out.println("pppppp"+responsePre.getString("resultMap"));
						FileOperation.writeFile(fileOut, resultCompare);
					}
				}else{
					String resultCompare = "【"+method+":"+id+"】"+url+"::::"+"返回code有错误的情况！！！！！！！！！！！！！！！";
					System.err.println(resultCompare);
					FileOperation.writeFile(fileOut,resultCompare);
				}
				if(castTimePre >castTimeMongo &&castTimePre>5000 ){
					String resultCompare = "【"+method+":"+id+"】::::"+"返回时间大于5秒！！！！！！！！！！！！！！！";
					System.err.println(resultCompare);
					FileOperation.writeFile(fileOut,resultCompare);
				}
			});
		});
		}catch(Exception e){
			 String  errorMsg = "【"+method+"】:--"+ e.getMessage();
			 System.err.println(errorMsg);
			 FileOperation.writeFile(fileOut,errorMsg);
			 e.printStackTrace();
		}
	}



	/**
	 * 启动线程池来执行case
	 * 存储mongo
	 */
	public void controllerTSaveMongo(List<String> list,String fileOut) {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		List<String> listM = getDoChannel();
		for (String m : listM) {
			List<String> listKeys = list.stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i < listKeys.size(); i++) {
				final int a = i;
				try{
					pool.execute(()->{
						Long time = System.currentTimeMillis();
						String url = address + m;
						String json = listKeys.get(a)==""?"{\"code\":\"JSON_ERR\"}":listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=")+1);
						JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						System.err.println(jsonResultPre);
						if(jsonResultPre.containsKey("resultMap")){
							insertMongo(time+a+"",jsonResultPre.getString("castTime"),time.toString(),json,jsonResultPre.getString("resultMap"));
						}else{
							insertMongo(time+a+"","",time.toString(),json,"{\"error\":\"结果未返回\"}");
						}
					});
				}catch(Exception e){
//					insertMongo("id", "错误码："+switchDocker+a,"异常："+e.getMessage());
					Long time = System.currentTimeMillis();
					insertMongo(time+a+"","",time.toString(), "{\"error-NO\":\""+switchDocker+a+"\"}","{\"error-code\":\""+e.getMessage()+"\"}");
					FileOperation.writeFile(fileOut,e.getMessage());
				}
			}
		}
	}


	//插入mongo
	public void insertMongo(String key,String castTime,String cteateTime,String requestBody,String responseBody){
		Map<String,Object> map = Maps.newHashMap();
		map.put("_id",key);
		map.put("castTime",castTime);
		map.put("cteateTime",cteateTime);
		map.put("request",JSONObject.parseObject(requestBody));
		map.put("response",JSONObject.parseObject(responseBody));
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
		MongoDao.insertMongoLong(dbConn,jsonObject,Long.parseLong(key));
	}

	//读取Mongo
	public static List<String> findMongo(){
		JSONArray mongoArray = MongoDao.queryByRegex(dbConn,"_id","152");
		System.out.print(mongoArray);
		return JSONArray.parseArray(mongoArray.toString(),String.class);
	}

	/**
	 * 传入jsonObject，获取map并比较
	 * @param jsonOne
	 * @param jsonTwo
	 * @return
	 */
	public String compareResult(JSONObject jsonOne,JSONObject jsonTwo){
		if(jsonOne.isEmpty()||jsonTwo.isEmpty()){
          System.out.println("jsonResut 返回有为空的");
		}else{
			String jsonMapOne = JSONObject.toJSONString(jsonOne.get("resultMap"), SerializerFeature.WriteMapNullValue);
			Map<String,Object> mapOne = JSONObject.parseObject(jsonMapOne);
			String jsonMapTwo = JSONObject.toJSONString(jsonTwo.get("resultMap"), SerializerFeature.WriteMapNullValue);
			Map<String,Object> mapTwo = JSONObject.parseObject(jsonMapTwo);
			return MapCompareTools.compareResult(mapOne,mapTwo);
		}
		return "jsonResut 返回有为空的";
	}

	/**
	 * 传入jsonObject，获取map并比较
	 * @param jsonOne
	 * @param jsonTwo
	 * @return
	 */
	public String compareResultNoResultMap(JSONObject jsonOne,JSONObject jsonTwo){
		if(jsonOne.isEmpty()||jsonTwo.isEmpty()){
			System.out.println("jsonResut 返回有为空的");
		}else{
			String jsonMapOne ="";
			if(jsonOne.containsKey("resultMap")){
				jsonMapOne = JSONObject.toJSONString(jsonOne.get("resultMap"), SerializerFeature.WriteMapNullValue);
			}else{
				jsonMapOne = JSONObject.toJSONString(jsonOne, SerializerFeature.WriteMapNullValue);
			}
			Map<String,Object> mapOne = JSONObject.parseObject(jsonMapOne);
			String jsonMapTwo ="";
			if(jsonTwo.containsKey("resultMap")){
				jsonMapTwo = JSONObject.toJSONString(jsonTwo.get("resultMap"), SerializerFeature.WriteMapNullValue);
			}else{
				jsonMapTwo = JSONObject.toJSONString(jsonTwo, SerializerFeature.WriteMapNullValue);
			}
			Map<String,Object> mapTwo = JSONObject.parseObject(jsonMapTwo);
			return MapCompareTools.compareResult(mapOne,mapTwo);
		}
		return "jsonResut 返回有为空的";
	}


	// 获取执行方法
	public List<String> getDoChannel() throws NullPointerException {
		List<String> listMethod = Lists.newArrayList();
		if (method.contains(",")) {
			listMethod = Arrays.asList(method.split(","));
		} else {
			listMethod.add(method);
		}
		return listMethod;
	}


     //	根据jdbc.properties文件给获取相应的oauth
	public static Map<String,Object> headerPut(){
		Map<String, Object> map = Maps.newHashMap();
		address = PropersTools.getValue(switchDocker + ".address");
		if(address.contains("cif-utc-rest")) {
			if(address.contains("api.puhuifinance.com")) {
				map = Oauth.getHeader("oauth_line_rest");
			}else{
				map = Oauth.getHeader("oauth_test_rest");
			}
		}
		if(address.contains("cif-rest-server")) {
			if(address.contains("api.puhuifinance.com")){
				map = Oauth.getHeader("oauth_line_server");
			}else{
				map = Oauth.getHeader("oauth_test_server");
			}
		}
		return map;
	}
}
