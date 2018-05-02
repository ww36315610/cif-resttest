package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.java.MapCompareTools;
import com.cif.utils.java.Oauth;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class HttpClientRequestDemo_Liunx {

	private static final String switchDocker = PropersTools.getValue("switch");
	private static final String method = PropersTools.getValue("method");
	private Pair<String, List<String>> pair;
	private static Map<String, Object> header;
	private static String address;
	private RestfulDao rd;

	static{
		header = headerPut();
	}

	public HttpClientRequestDemo_Liunx(RestfulDao restfulDao){
		this.rd = restfulDao;
	}

	public static void main(String[] args) {
		RestfulDao restfulDao = new RestfullDaoImp();
		HttpClientRequestDemo_Liunx hcrd = new HttpClientRequestDemo_Liunx(restfulDao);
//		hcrd.controller();
//		hcrd.controllerT();
//		hcrd.caseMake();
		final String before = "cif-utc-rest-pre";
		final String after = "cif-utc-rest";
		//按照jdbc.properties的case走
//		List<String> list = PropersTools.getKeys();
		//按照读取csv的case走
//		List<String> listCSV = CsvReadTools.getCaseFromCSV(System.getProperty("fileCSV"));
		List<String> list = args.length>1?CsvReadTools.getCaseFromCSV(System.getProperty("fileCSV")):PropersTools.getKeys();
		System.out.println("-----------"+list.size());
        int threadIteritor = args.length>0?Integer.parseInt(args[0]):Integer.parseInt(System.getProperty("threadNum"));
		for (int i = 0; i < threadIteritor; i++) {
			new Thread(new Runnable() {
				public void run() {
					hcrd.controllerTCompare(list,before,after,System.getProperty("fileOut"),args);
				}
			}).start();

		}
	}
	//
	//

    /**
     * 打开执行开关，并执行对应case【单线程】
	 */
	public void controller() {
		List<String> listMethod = getDoChannel();
		for (String m : listMethod) {
			String url = address + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
//					String urlReplace = url.replace();
					System.out.println("【"+i+"】"+jsonResult);
				}catch(Exception e){
					System.out.println("【"+i+"】:--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 启动线程池来执行case
	 */
	public void controllerT() {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = address + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				final int a = i;
				try{
					pool.execute(()->{
						String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
						JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						System.out.println("【"+a+"】"+jsonResult);
					});
				}catch(Exception e){
					String  errorMsg = "【"+a+"】:--"+ e.getMessage();
					System.err.println(errorMsg);
					FileOperation.writeFile("/Users/apple/Documents/linlin/error_0328.txt",errorMsg);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 启动线程池来执行case
	 * 返回map比较
	 */
	public void controllerTCompare(List<String> list,String beforeUrl,String afterUrl,String fileOut,String[] args) {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		List<String> listM = getDoChannel();
		for (String m : listM) {
//			String url = address + m;
			List<String> listKeys = list.stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i < listKeys.size(); i++) {
				final int a = i;
				try{
					pool.execute(()->{
						String url = address + m;
						String json="";
						if(args.length>1) {
							json = listKeys.get(a) == "" ? "{\"code\":\"JSON_ERR\"}" : listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=") + 1);
						}else{
						 json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
						}
						JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						//System.out.println("pree:::[[["+a+"]]]]"+jsonResultPre);
                        url = url.replace(beforeUrl,afterUrl);
						JSONObject jsonResultLine = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						//System.out.println("line:::[[["+a+"]]]]"+jsonResultLine);
						if(!(jsonResultLine.getString("resultMap").contains("code#####")||jsonResultPre.getString("resultMap").contains("code#####"))) {
							System.err.println(compareResult(jsonResultPre,jsonResultLine)=="Same map"?"True【"+m+":"+a+"】":"False【"+m+":"+a+"】"+json);
						}else{
							System.err.println("【"+m+":"+a+"】"+url+"::::"+"返回code有错误的情况！！！！！！！！！！！！！！！");
						}
					});
				}catch(Exception e){
					String  errorMsg = "【"+a+"】:--"+ e.getMessage();
					System.err.println(errorMsg);
					FileOperation.writeFile(fileOut,errorMsg);
//					FileOperation.writeFile("/Users/apple/Documents/linlin/error_0328.txt",errorMsg);
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 启动线程池来执行case
	 * 返回map比较
	 */
	public void controllerTCompareList(List<String> list, String beforeUrl,String afterUrl,String fileOut) {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		List<String> listM = getDoChannel();
		for (String m : listM) {
//			String url = address + m;
			List<String> listKeys = list.stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				final int a = i;
				try{
					pool.execute(()->{
						String url = address + m;
//                        String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
                        String json = listKeys.get(a)==""?"{\"code\":\"JSON_ERR\"}":listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=")+1);
						JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						url = url.replace(beforeUrl,afterUrl);
						JSONObject jsonResultLine = (JSONObject) rd.getJsonArray(url, header, json).get(0);
						if(!(jsonResultLine.getString("resultMap").contains("code#####")||jsonResultPre.getString("resultMap").contains("code#####"))) {
							System.err.println(compareResult(jsonResultPre,jsonResultLine)=="Same map"?"True【"+m+":"+a+"】":"False【"+m+":"+a+"】"+json);
						}else{
							System.err.println("【"+m+":"+a+"】"+url+"::::"+"返回code有错误的情况！！！！！！！！！！！！！！！");
						}
					});
				}catch(Exception e){
					String  errorMsg = "【"+a+"】:--"+ e.getMessage();
					System.err.println(errorMsg);
					FileOperation.writeFile(fileOut,errorMsg);
//					FileOperation.writeFile("/Users/apple/Documents/linlin/error_0328.txt",errorMsg);
					e.printStackTrace();
				}
			}
		}
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


	public void caseMake() {
		ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = address + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				final int a = i;
				try{
					pool.execute(()->{
						String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
					System.out.println(switchDocker + "." + m + "_" + a+"="+json.split("###")[0]);
//						FileOperation.writeFile("/Users/apple/Documents/linlin/case_0101.txt",json);
					});
				}catch(Exception e){
					System.err.println("【"+a+"】:--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
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
