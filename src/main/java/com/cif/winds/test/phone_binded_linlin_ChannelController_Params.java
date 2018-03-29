package com.cif.winds.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

public class phone_binded_linlin_ChannelController_Params {

	private static String switchDocker = PropersTools.getValue("switch");
	private static String method = PropersTools.getValue("method");
	private Pair<String, List<String>> pair;
	static Map<String, Object> map = new HashMap<String, Object>();
	RestfulDao rd = new RestfullDaoImp();


	private static final String filePathS="/Users/apple/Documents/linlin/0328/";
	//	private final static String filePath = "/Users/apple/Documents/linlin_idno_result.txt";
	//	private final static String filePath = "/Users/apple/Documents/linlin_mobile_result.txt";
	//	private static final String filePath=filePathS + "linlin_[user_id]_result.txt";
	//  private static final String filePath=ilePathS + "linlin_[contacter_mobile]_result.txt";
	//  private static final String filePath=filePathS + "linlin_[company_tel]_result.txt";
	//  private static final String filePath=filePathS + "linlin_[bank_mobile]_result.txt";

	//  private static final String filePath=filePathS + "linlin_[bank_card_pay_id]_result.txt";
	  private static final String filePath=filePathS + "linlin_[phone_binded]_result.txt";
//	  private static final String filePath=filePathS + "linlin_[login_email]_result.txt";
//      private static final String filePath=filePathS + "linlin_[member_name]_result.txt";
//      private static final String filePath=filePathS + "linlin_[email]_result.txt";

	static {
		String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
		TagsRequest tr = new TagsRequest();
		OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
		// 设置header
		map.put("Accept", "*/*");
		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
		map.put("Content-Type", "application/json");
	}


	// 打开执行开关，并执行对应case
	public void controller0(RestfulDao rd) {
		List<String> listM = getDoChannel();
		for (String m : listM) {
			String url = PropersTools.getValue(switchDocker + ".address") + m;
			List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
				return keysFilter.contains(switchDocker + "." + m);
			}).collect(Collectors.toList());
			for (int i = 1; i <= listKeys.size(); i++) {
				String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
				JSONArray jsonResult = rd.getJsonArray(url, map, json);
				JSONObject jsonObject =  jsonResult.getJSONObject(0);
				String string = JSONObject.toJSONString(jsonObject.getJSONObject("resultMap"), SerializerFeature.WriteMapNullValue);
				String result = json.substring(json.indexOf("{"))+"#####"+string;
				FileOperation.writeFile(filePath,result);
				System.out.println(result);
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
		phone_binded_linlin_ChannelController_Params ucp = new phone_binded_linlin_ChannelController_Params();
		ucp.controller0(rd);
	}
}
