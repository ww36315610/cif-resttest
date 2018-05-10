package com.cif.winds.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.java.MapCompareTools;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class assert_ChannelController_Params {

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



//	static {
//		TagsRequest tr = new TagsRequest();
//		OAuth2AccessToken token = Oauth.getTokenLine();
//		// 设置header
//		map.put("Accept", "*/*");
//		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
//		map.put("Content-Type", "application/json");
//	}

//	static {
//		TagsRequest tr = new TagsRequest();
//		OAuth2AccessToken token = Oauth.getToken();
//		// 设置header
//		map.put("Accept", "*/*");
//		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
//		map.put("Content-Type", "application/json");
//	}
	// 打开执行开关，并执行对应case
	public void controller(RestfulDao rd) {
		String fileOut = "/Users/apple/Documents/result_1000/pre-0502.txt";
		String json=null;
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
//					System.out.println(jsonResult);
					String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"),SerializerFeature.WriteMapNullValue);
					String slowSql ="【"+i+"】"+jsonRR+"----"+jsonResult.get("castTime");
//                    String slowSql ="【"+i+"】"+"----"+jsonResult.get("castTime");
					System.out.println(slowSql);
					if(Integer.parseInt(jsonResult.getString("castTime"))>3000)
						FileOperation.writeFile(fileOut,slowSql);
					if (jsonResult.getInteger("failCount") > 0) {
						Map<String,Object> map  = Maps.newHashMap();
						map = JSONObject.parseObject(json).getJSONObject("params");
						System.err.println(jsonResult);
					if((jsonResult.getString("failTagReasonMap").contains("doesn't exist"))||(jsonResult.getString("failTagReasonMap").contains("does not exist"))){
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
//						JSONObject failJson = JSONObject.parseObject(jsonResult.getString("failTagReasonMap"));
						System.err.println("["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
						FileOperation.writeFile(fileOut,"["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
					}else{
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
						System.out.println("["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
						FileOperation.writeFile(fileOut,"["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
					}
				} else {
//					System.err.println("【" + i + "】" + jsonResult);
				}
			}catch(Exception e){
					String ejson = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
					FileOperation.writeFile(fileOut,"【"+i+"】:"+ejson +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}


	// 测试凡卡-es-hbase
	public void controllerUTC(RestfulDao rd) {
		String fileOut = "/Users/apple/Documents/result_1000/pre-slow_new3900_0413.txt";
		String json=null;
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m+"_");
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
//					System.out.println(json);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
//					System.out.println("【"+i+"】"+jsonResult);
                    System.out.println("【"+i+"】"+JSONObject.toJSONString(jsonResult.get("resultMap"),SerializerFeature.WriteMapNullValue));
				}catch(Exception e){
//					String ejson = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
//					FileOperation.writeFile(fileOut,"【"+i+"】:"+ejson +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}

	// 执行count-sum-avg函数
	public void controllerHanshu(RestfulDao rd) {
		Map<String,Object> mapResult =null;
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
					String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"),SerializerFeature.WriteMapNullValue);
					String tagName = JSONObject.parseObject(json).getString("tagName");
					JSONObject asNameJSON = JSONObject.parseObject(jsonRR).getJSONArray(tagName).getJSONObject(0);
					String slowSql =asNameJSON.getString("result");
//					String slowSql ="【"+i+"】"+asNameJSON.getString("result");
					mapResult = JSONObject.parseObject(slowSql);
					System.out.println(slowSql);
					if(Integer.parseInt(jsonResult.getString("castTime"))>3000)
						FileOperation.writeFile("/Users/apple/Documents/result_1000/slow_new38001.txt",slowSql);
					if (jsonResult.getInteger("failCount") > 0) {
						Map<String,Object> map  = Maps.newHashMap();
						map = JSONObject.parseObject(json).getJSONObject("params");
						if((jsonResult.getString("failTagReasonMap").contains("doesn't exist"))||(jsonResult.getString("failTagReasonMap").contains("does not exist"))){
                           System.err.println("["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
						}else{
							System.out.println("["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
						}
					} else {
//						System.err.println("【" + i + "】" + jsonResult);
					}
				}catch(Exception e){
					FileOperation.writeFile("/Users/apple/Documents/result_1000/slow_new38001.txt","【"+i+"】" + e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}


	//帮助周旭测试标签
	public void zhouController(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				try{
					String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
					if(json.contains("$mobile_list")){
						json = json.replace("$mobile_list",crawler_interface.controller(rd));
					}
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
					String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"),SerializerFeature.WriteMapNullValue);
					System.out.println("【"+i+"】"+jsonRR);
					if (jsonResult.getInteger("failCount") > 0) {
						Map<String,Object> map  = Maps.newHashMap();
						map = JSONObject.parseObject(json).getJSONObject("params");
						if((jsonResult.getString("failTagReasonMap").contains("doesn't exist"))||(jsonResult.getString("failTagReasonMap").contains("does not exist"))){
                           System.err.println("["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
						}else{
							System.out.println("["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
						}
					} else {
					System.err.println("【" + i + "】" + jsonResult);
					}
				}catch(Exception e){e.printStackTrace();}
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


	// 打开执行开关，并执行对应case
	public Map<String,Object> controllerReturnMap(RestfulDao rd) {
		Map<String,Object> mapResult =null;
		String fileOut = "/Users/apple/Documents/result_1000/pre-slow_new3900_0417.txt";
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
					JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
//					System.out.println(jsonResult);
					String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"),SerializerFeature.WriteMapNullValue);
					String slowSql ="【"+i+"】"+jsonRR+"----"+jsonResult.get("castTime");
					mapResult = JSONObject.parseObject(jsonRR);
					System.out.println(mapResult);
					if(Integer.parseInt(jsonResult.getString("castTime"))>3000)
						FileOperation.writeFile(fileOut,slowSql);
					if (jsonResult.getInteger("failCount") > 0) {
						Map<String,Object> map  = Maps.newHashMap();
						map = JSONObject.parseObject(json).getJSONObject("params");
						System.err.println(jsonResult);
						if((jsonResult.getString("failTagReasonMap").contains("doesn't exist"))||(jsonResult.getString("failTagReasonMap").contains("does not exist"))){
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
//						JSONObject failJson = JSONObject.parseObject(jsonResult.getString("failTagReasonMap"));
							System.err.println("["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
							FileOperation.writeFile(fileOut,"["+i+":"+map.get("tagName")+"]：Table Exists：：："+jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
						}else{
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
							System.out.println("["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
							FileOperation.writeFile(fileOut,"["+i+":"+map.get("tagName")+"]：BADSQL：："+jsonResult.getString("failTagReasonMap"));
						}
					} else {
//					System.err.println("【" + i + "】" + jsonResult);
					}
				}catch(Exception e){
					String ejson = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
					FileOperation.writeFile(fileOut,"【"+i+"】:"+ejson +"--"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return mapResult;
	}


//	String receiveRequestFallBack(String id, Throwable e) {
//
//		assert "getUserById command failed".equals(e.getMessage());
//
//		throw new RuntimeException("receiveRequestFallBack failed.");
//
//	}

//	@HystrixCommand(
//			fallbackMethod = "receiveRequestFallBack",
//			ignoreExceptions = {HystrixBadRequestException.class},
//			commandProperties = {
//					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//					@HystrixProperty(name = "execution.timeout.enabled", value = "true")
//			}
//	)
	public static void main(String[] args) throws Exception {
		RestfulDao rd = new RestfullDaoImp();
		assert_ChannelController_Params ucp = new assert_ChannelController_Params();
//		ucp.controller(rd);
//		ucp.controllerHanshu(rd);

		for (int i=0;i<100;i++)
			ucp.controllerUTC(rd);



//        String mapCompare = "{\"mo_sms_send_cnt_1m\":[{\"sms_send_cnt_1m\":0}],\"mo_call_cnt_before_dawn_ratio_6m\":[{\"call_cnt_before_dawn_ratio_6m\":0.0}],\"mo_if_dial_type\":[{\"calltype\":0}],\"mo_call_out_duration_deep_night_3m\":[{\"call_out_duration_deep_night_3m\":0}],\"mo_call_cnt_noon_weekday_3m\":[{\"call_cnt_noon_weekday_3m\":6}],\"mo_call_1min_am_cnt_6m\":[{\"call_1min_am_cnt_6m\":55}],\"mo_cnt_sum_180d\":[{\"cnt_dailed_180\":318}],\"mo_call_deep_night_adpc_6m\":[{\"call_deep_night_adpc_6m\":27.0}],\"mo_cnt_sum_no_180d\":[{\"cnt_sum_no_180d\":41}],\"mo_cnt_sum_no_90d\":[{\"cnt_sum_no_90d\":35}],\"mo_phonedays\":[{\"phonedays\":427}],\"mo_avg_sum_duration_90d\":[{\"avg_sum_duration_90\":48.455}],\"mo_cnt_sum_90d\":[{\"cnt_dailed_90\":211}]}";
//		String mapCompare = "{\"resultMap\":{\"mo_sms_send_cnt_1m\":[{\"sms_send_cnt_1m\":28}],\"mo_call_cnt_before_dawn_ratio_6m\":[{\"call_cnt_before_dawn_ratio_6m\":0.01}],\"mo_if_dial_type\":[{\"calltype\":0}],\"mo_call_out_duration_deep_night_3m\":[{\"call_out_duration_deep_night_3m\":16.0}],\"mo_call_cnt_noon_weekday_3m\":[{\"call_cnt_noon_weekday_3m\":83}],\"mo_call_1min_am_cnt_6m\":[{\"call_1min_am_cnt_6m\":544}],\"mo_cnt_sum_180d\":[{\"cnt_dailed_180\":3962}],\"mo_call_deep_night_adpc_6m\":[{\"call_deep_night_adpc_6m\":50.25}],\"mo_cnt_sum_no_180d\":[{\"cnt_sum_no_180d\":826}],\"mo_cnt_sum_no_90d\":[{\"cnt_sum_no_90d\":288}],\"mo_phonedays\":[{\"phonedays\":1127}],\"mo_avg_sum_duration_90d\":[{\"avg_sum_duration_90\":104.8048}],\"mo_cnt_sum_90d\":[{\"cnt_dailed_90\":1583}]}}";

//      String mapCompare ="{\"ss_id_no\":[{\"id_card\":\"310109198912131030\"}],\"ss_name\":[{\"real_name\":\"胡天杰\"}],\"ss_work_status\":[{}],\"ss_cnt_stop_2y\":[{\"stop_cnt\":2}],\"ss_acc_age\":[{\"months\":1467302400000}],\"ss_cnt_corp_24m\":[{\"corporation_name_cnt_24\":3}],\"ss_city\":[{\"city\":\"上海\"}]}";



//		String mapCompare ="{\"15042319751201642X\":[{\"credit_guarantee_num\":0,\"other_outsstanding_num\":0,\"other_guarantee_num\":0,\"在贷余额\":\"无\",\"house_loan_guarantee_num\":0,\"other_account_num\":0,\"强制执行记录\":\"无\",\"credit_overdue_num\":4,\"house_loan_outsstanding_num\":0,\"credit_account_num\":6,\"身份证号\":\"xy87c604e255e7a35f45bb0f7c42ca83411e7fba4877e86be8ea9313684385b7d820160926\",\"house_loan_overdue_num\":0,\"民事判决记录\":\"无\",\"欠税记录\":\"无\",\"贷款余额总数\":\"无\",\"电信欠费\":\"无\",\"借啊用户名\":\"无\",\"credit_overdue90_num\":0,\"征信报告\":\"无\",\"house_loan_overdue90_num\":0,\"lend_customer_basic\":[{\"education\":\"本科\",\"companyName\":\"内蒙古集通铁路（集团）有限责任公司\",\"jobTitle\":\"一般管理人员\",\"借款人受行政处罚情况\":\"未提供\",\"借款人涉诉情况\":\"未提供\",\"liveCity\":\"赤峰市\",\"id_no\":\"xy87c604e255e7a35f45bb0f7c42ca83411e7fba4877e86be8ea9313684385b7d820160926\",\"companyPhone\":\"0476-2272946\",\"homeCity\":null,\"workCity\":\"赤峰市\",\"companyNature\":\"国企/上市公司\",\"email\":\"2720806662@qq.com\",\"homeAddress\":null,\"workYears\":\"9.3\",\"utmSource\":\"普惠金融\",\"liveAddress\":\"翁牛特旗乌丹镇书香家园2号楼2单元401\",\"nickName\":\"ph20171211108770480\",\"sex\":\"女\",\"houseType\":\"自有房产\",\"liveProvince\":\"内蒙古自治区\",\"workProvince\":\"内蒙古自治区\",\"借款人经营状况及财务情况\":\"无变化\",\"借款人还款能力变化情况\":\"无变化\",\"homeProvince\":null,\"companyAddress\":4003107,\"资金运用情况\":\"已使用\",\"maritalStatus\":\"已婚\"}],\"credit_outsstanding_num\":6,\"other_overdue90_num\":0,\"行政处罚\":\"无\",\"house_loan_account_num\":0,\"other_overdue_num\":0}]}";
//
//        Map<String,Object> before =  JSONObject.parseObject(mapCompare,Map.class);
//
//		String mm = JSONArray.parseArray(before.get("15042319751201642X").toString()).get(0).toString();
//		Map<String,Object> beforee = JSONObject.parseObject(mm,Map.class);
//		for (int i = 0;i<10;i++) {
//			Map<String, Object> mapNow = ucp.controllerReturnMap(rd);
//			String mpaNoww = JSONArray.parseArray(mapNow.get("15042319751201642X").toString()).get(0).toString();
//			Map<String,Object> mapNoww = JSONObject.parseObject(mpaNoww,Map.class);
//				System.err.println(MapCompareTools.compareResult(beforee, mapNoww));
//		}

    }

}
