package com.cif.winds.test;

import com.alibaba.fastjson.JSONArray;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.*;

public class newLine_ChannelController_Params {

	private static String switchDocker = PropersTools.getValue("switch");
	private static String method = PropersTools.getValue("method");
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
//		map.put("Content-Type", "application/json");
		map.put("Content-Type", "application/json;charset=UTF-8");
	}


	// 打开执行开关，并执行对应case
	public void controller(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String jsonMsg= PropersTools.getValue(switchDocker + "." + m + "_" + i);
				String json = jsonMsg.split("###")[0];
				String r1 = jsonMsg.split("###")[1];
				String r2 = jsonMsg.split("###")[2];
				JSONArray jsonResult = rd.getJsonArray(url, map, json);

				System.out.println(jsonResult.getJSONObject(0).getJSONObject("resultMap"));
				if(jsonResult.getJSONObject(0).getJSONObject("resultMap").toJSONString().equals(r1)){
					System.out.println("111111");
				}
			}
		}
	}


	// 打开执行开关，并执行对应case
	public void controller1(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String jsonMsg = PropersTools.getValue(switchDocker + "." + m + "_" + i);
				String json = jsonMsg.split("###")[0];
				String r1 = jsonMsg.split("###")[1];
				String r2 = jsonMsg.split("###")[2];
				JSONObject jsonResult = rd.getJsonObject(url, map, json);
				JSONObject resultMap = jsonResult.getJSONObject("resultMap");
				String resultMapstring = JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
				String tagName =r1.substring(r1.indexOf("{")+2,r1.indexOf(":")-1);
//				String tagName1 =
				if(r1.equals(resultMapstring)) {
					System.out.println("【" + i + "】" + resultMapstring);
//					String resultMySub1 = resultMapstring.substring(resultMapstring.lastIndexOf(":")+1);
//					String resultMySub2 = resultMySub1.substring(0,resultMySub1.indexOf("}"));
////					System.out.println("【" + i + "】"+resultMySub2);
//					if(r2.contains("(null)")){
//						r2 = "null";
//					}
//					if(r2.equals(resultMySub2)){
//						System.out.println("【" + i + "】业务方根tag比对通过！！！！！1");
//					}else{
//						System.err.println("【" + i + "】:【"+tagName+"】比对失败了！！！！！2，Tag:【"+resultMySub2+"】==MA:【"+r2+"】");
//					}
				}else{
					System.err.println("【" + i + "】tag结果两次跑的结果不一致回归失败！！！！！3");
					String resultMySub1 = resultMapstring.substring(resultMapstring.lastIndexOf(":")+1);
					String resultMySub2 = resultMySub1.substring(0,resultMySub1.indexOf("}"));
				    System.out.println(r1 +"=---=" +resultMySub2);
				}
			}
		}
	}


//根据一方结果去造case
	public void controller2(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String jsonMsg = PropersTools.getValue(switchDocker + "." + m + "_" + i);
				String json = jsonMsg.split("###")[0];
				String r1 = jsonMsg.split("###")[1];
				JSONObject jsonResult = rd.getJsonObject(url, map, json);
				JSONObject resultMap = jsonResult.getJSONObject("resultMap");
				String resultMapstring = JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
				FileOperation.writeFile("/Users/apple/Documents/wwcc_line_taobao.txt","line.moxie_"+i+"="+json+"###"+resultMapstring);
//				System.out.println("line.moxie_"+i+"="+json+"###"+resultMapstring);

			}
		}
	}


	// pre环境根line环境进行比对
	public void controller3(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String jsonMsg = PropersTools.getValue(switchDocker + "." + m + "_" + i);
				String json = jsonMsg.split("###")[0];
				String r1 = jsonMsg.split("###")[1];
//				System.out.println("11"+url);
//				System.out.println("22"+map);
//				System.out.println("33"+json);
				JSONObject jsonResult = rd.getJsonObject(url, map, json);
				JSONObject resultMap = jsonResult.getJSONObject("resultMap");
				String resultMapstring = JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
				if(!resultMapstring.equals(r1)) {
					System.err.println("【" + i + "】" + r1 + "---" + resultMapstring);
				}else{
					System.out.println("["+i+"]");
				}
			}
		}
	}


	// pre环境根line环境进行比对
	public void controller4(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String jsonMsg = PropersTools.getValue(switchDocker + "." + m + "_" + i);
				String json = jsonMsg.split("###")[0];
				String r1 = jsonMsg.split("###")[1];
//				System.out.println("11"+url);
//				System.out.println("22"+map);
//				System.out.println("33"+json);
				JSONObject jsonResult = rd.getJsonObject(url, map, json);
//				if(jsonResult.equals("")){
//					System.err.println("【"+i+"】数据为空---MA："+r1+"pre："+jsonResult);
//				}
				try{
					JSONObject resultMap = jsonResult.getJSONObject("resultMap");
					String resultMapstring = JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
					String resultMySub1 = resultMapstring.substring(resultMapstring.lastIndexOf(":")+1);
					String resultMySub2 = resultMySub1.substring(0,resultMySub1.indexOf("}"));
					String r2 = r1;
					if(r1.contains(".")) {
						r2 = r1.substring(0, r1.indexOf("."));
					}
					if(!resultMySub2.equals(r2)) {
						FileOperation.writeFile("/Users/apple/Documents/result_1000/pre_compare_taobao5.txt","【" + i + "】" + r1 + "---" + resultMySub2);
						System.err.println("【" + i + "】" + r1 + "---" + resultMySub2);
//						System.err.println("【" + i + "】###"+json +"### MA:"+ r1 + "----pre:" + resultMySub2);
//					System.err.println("【" + i + "】" + r1 + "---" + jsonResult);

					}else{
//					System.out.println("["+i+"]---【MA："+r2 +"】---PRE："+resultMySub2);
					}
				}catch (Exception e){e.printStackTrace();}

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
		RestfulDao rd = new RestfullDaoImp();
		newLine_ChannelController_Params ucp = new newLine_ChannelController_Params();
		ucp.controller4(rd);
	}
}
