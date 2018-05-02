package com.cif.winds.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.utils.PropersTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import org.testng.Assert;

public class UAT_ChannelController_Params {

	private static String switchDocker = PropersTools.getValue("switch");
	private static String method = PropersTools.getValue("method");
	private Pair<String, List<String>> pair;
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();
	static {
		String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getToken(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
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
				String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
//				System.out.println(json);
				JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
				String rsultString = JSONObject.toJSONString(jsonResult.getJSONObject("resultMap"), SerializerFeature.WriteMapNullValue);
				System.out.println("【"+i+"】"+rsultString);
				if(i%32 ==0)System.out.println("**************更换场景******************");
				if(jsonResult.getInteger("failCount")>1){
					Assert.assertTrue(false,"【"+i+"】错误次数大于1");
				}
//				System.out.println(jsonResult);
				if (jsonResult.getInteger("failCount") > 0)
					System.out.println("【"+i+"】"+jsonResult);
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
		UAT_ChannelController_Params ucp = new UAT_ChannelController_Params();
//		ucp.controller(rd);

		for(int i=0;i<3;i++){
			new Thread(new Runnable(){
    		public void run(){
//				ucp.controller(rd);
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(i+":::::"+Thread.currentThread().getName());
				}
			}
		}).start();
	}


		// String name =null;
		// if(StringUtils.isBlank(name)){
		// System.out.println("aaaaaa");
		// }
	}
}
