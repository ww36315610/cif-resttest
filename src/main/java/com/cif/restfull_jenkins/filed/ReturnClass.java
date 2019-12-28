package com.cif.restfull_jenkins.filed;

import com.alibaba.fastjson.JSONObject;
import com.cif.restfull_jenkins.HttpResponse;

public class ReturnClass {
	public static HttpResponse getHttpClass(JSONObject object) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class httpClass = Class.forName("com.cif.restfull_jenkins.filed.HttpResponse_" + object.getString("channel"));
		HttpResponse httpResponse = (HttpResponse) httpClass.newInstance();
		return httpResponse;
	}
}
