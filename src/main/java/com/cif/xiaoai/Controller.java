package com.cif.xiaoai;

import java.util.ArrayList;
import java.util.List;

public class Controller {

	static String httpUrl = "http://10.10.22.204:8898/xa/getTagsByIdNo";
	static String httpFilterKey = "idNo";
	// static String httpFilterKey = "cellPhone";
	static String httpFilterValue = "110101196004254513";

	static String aa = "idNo";
	static String bb = "cellPhone";
	static String idNo = "330321197201203936";
	static String cellPhone = "15929698605";
	static List<Object> paramKey = new ArrayList<Object>();
	static List<Object> paramValue = new ArrayList<Object>();

	public static void main(String[] args) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put(httpFilterKey, httpFilterValue);
		// QueryData qd = new QueryData(httpUrl, httpFilterKey,
		// httpFilterValue);
		// System.out.println("---------" + qd.getJsonArray());

		paramKey.add(aa);
//		paramKey.add(bb);
		paramValue.add(idNo);
//		paramValue.add(cellPhone);
		QueryDataT qd = new QueryDataT(httpUrl, paramKey, paramValue);
		System.out.println("---------" + qd.getJsonArray());
	}
}