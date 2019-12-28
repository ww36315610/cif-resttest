package com.cif.winds.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class crawler_interface {

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
	public static String controller(RestfulDao rd) {
//		String url = "http://ut1.zuul.pub.puhuifinance.com:8765/datapi-sdk-api/sdk/query?requestId=106201703150004891&dataType=sdk_gsm_info";
		String url ="http://10.10.246.158:7083/openapi/okamoto/selectMobileIntegrate?applyNo=325201804080999160";
		JSONObject jsonResult = (JSONObject) rd.getJsonArrayGet(url, map).get(0);
		JSONArray jsonPhone = jsonResult.getJSONObject("result").getJSONObject("mobileDetailGsm").getJSONArray("201804");
		String returnPhone=null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<jsonPhone.size();i++){

			JSONObject jr  = (JSONObject) jsonPhone.get(i);
			returnPhone = jr.get("counterpartPhone").toString();
			if(i<jsonPhone.size()-1){
			sb.append(returnPhone+"','");
			}else{
				sb.append(returnPhone);
			}
			returnPhone = sb.toString();
		}
		System.out.println(returnPhone);
		return returnPhone;

	}

	public static void main(String[] args) throws Exception {
		RestfulDao rd = new RestfullDaoImp();
		crawler_interface ucp = new crawler_interface();
		ucp.controller(rd);
    }
}
