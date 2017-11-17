package com.cif.utcs_jenkins;

import java.util.Iterator;
import java.util.List;

import com.cif.utils.file.FileOperation;

public class Run {
	static String fileName = QueryData.class.getClassLoader().getResource("fromTable.txt").getPath();
	// static String httpUrl = "http://192.168.136.31:8898//tags/submit_sql";
	// static String httpFilterKey = "idNo";
	// static String httpFilterValue = "370102197511012913";

	static String httpUrl = "http://10.10.28.5:8898//tags/submit_sql";
	// static String httpFilterKey = "idNo";
	// static String httpFilterValue = "130681198610135559";
	static String httpFilterKey = "idCardNum";
	static String httpFilterValue = "xye0d4f3d0b0e0dfa648c5ec5dbb94e69a20160926";

	static List<String> list = FileOperation.readFileByLineString(fileName);

	public static void main(String[] args) {
		for (Iterator itor = list.iterator(); itor.hasNext();) {
			String tableName = itor.next().toString();
			String sql = "SELECT * FROM " + tableName;
			QueryData qd = new QueryData(httpUrl, httpFilterKey, httpFilterValue, sql);
			if (qd.getJsonArray().size() > 1) {
				System.out.println("【"+tableName+"】" + qd.getJsonArray());
			}
		}
	}
}
