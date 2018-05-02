package com.cif.winds.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Apache_cli_UTCS {

	private static String switchDocker = PropersTools.getValue("switch");
	private static String method = PropersTools.getValue("method");
	private Pair<String, List<String>> pair;
	static Map<String, Object> map = new HashMap<String, Object>();
	static Map<String, Object> mapTest = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json;charset=UTF-8");

		String autoUrlTest = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		OAuth2AccessToken tokenTest = Oauth.getToken(autoUrlTest);
		// 设置header
		mapTest.put("Accept", "*/*");
		mapTest.put("Authorization", String.format("%s %s", tokenTest.getTokenType(), tokenTest.getValue()));
		mapTest.put("Content-Type", "application/json;charset=UTF-8");
	}

	// 打开执行开关，并执行对应case
	public void controller(RestfulDao rd,Map<String, Object> mm ) {
		String json=null;
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, mm, json).get(0);
					System.out.println("【"+i+"】"+jsonResult);
			}catch(Exception e){
					json = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
					System.out.println("【"+i+"】:"+json +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	String jsonThread = null;
	int a = 1;
	// 多线程打开执行开关，并执行对应case
	public void controllerThread(RestfulDao rd,Map<String, Object> mm ) {
//		String json=null;
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());

			List<JSONArray> jsonResultList = rd.getJsonArrayByThread(url,mm,listKeys);

			for (int i = 1; i <= listKeys.size(); i++) {
				a=i;
				try{
					jsonThread = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					ThreadPoolUtils.getThreadPoolExecutor().execute(() -> {
						a++;
						JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, mm, jsonThread).get(0);
						System.out.println("【" + a + "】" + jsonResult);
					});
				}catch(Exception e){
					String ejson = JSONObject.parseObject(jsonThread).getJSONObject("params").getString("tagName");
					System.out.println("【"+i+"】:"+ejson +"--"+ e.getMessage());
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


    public static void main(String[] args) throws Exception {
//		resultC(args);
		resultCThread(args);
	}
	public static void result(String[] args) {
		Options opts = new Options();
		opts.addOption("h", "help", false, "This is help!");
		opts.addOption("l", "line", true, "line for utcs");
		opts.addOption("t", "file", true, "test fro utcs");
		BasicParser parser = new BasicParser();
		CommandLine cl;
		RestfulDao rd = new RestfullDaoImp();
		Apache_cli_UTCS ucp = new Apache_cli_UTCS();
		try {
			cl = parser.parse(opts, args);
			if (cl.getOptions().length > 0) {
				if (cl.hasOption('h')) {
					HelpFormatter hf = new HelpFormatter();
					hf.printHelp("Options", opts);
				} else {
					if (cl.hasOption('l')) {
						ucp.controller(rd,map);
					} else if (cl.hasOption('t')) {
						ucp.controller(rd,mapTest);
					} else {
						ucp.controller(rd,mapTest);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void resultC(String[] args) {
		RestfulDao rd = new RestfullDaoImp();
		Apache_cli_UTCS ucp = new Apache_cli_UTCS();
		try {
		Map<String, Object> mpp = Maps.newHashMap();
		mpp= args.length>0?map:mapTest;
		 ucp.controller(rd,mpp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }

	public static void resultCThread(String[] args) {
		RestfulDao rd = new RestfullDaoImp();
		Apache_cli_UTCS ucp = new Apache_cli_UTCS();
		try {
			Map<String, Object> mpp = Maps.newHashMap();
			mpp= args.length>0?map:mapTest;
			ucp.controllerThread(rd,mpp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
