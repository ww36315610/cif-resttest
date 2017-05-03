package com.cif.utils.fanshe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class FieldFilter {

	// // 反射获取mysql对应的执行对象
	// public static DoFilter_Mysql getDoFilterMysql(JSONObject object) throws
	// ClassNotFoundException,
	// InstantiationException, IllegalAccessException {
	// DoFilter_Mysql obj = null;
	// Class c = Class.forName(getClassName(object));
	// obj = (DoFilter_Mysql) c.newInstance();
	// return obj;
	// }
	//
	// // 反射获取hive对应的执行对象
	// public static DoFilter_Hive getDoFilterHive(JSONObject object) throws
	// ClassNotFoundException,
	// InstantiationException, IllegalAccessException {
	// DoFilter_Hive obj = null;
	// Class c = Class.forName(getClassName(object));
	// obj = (DoFilter_Hive) c.newInstance();
	// return obj;
	// }

	// 反射得到bean类中的属性信息，className后期可以通过反射获取，也做到了关键字渠道
	public static List<String> getBeanFilter(JSONObject object) throws ClassNotFoundException {
		List<String> list = new ArrayList<String>();
		Class clazz = Class.forName(getBeanClassName(object));
		Field fields[] = clazz.getDeclaredFields();
		for (Field colum : fields) {
			colum.setAccessible(true);
			list.add(colum.getName());
		}
		return list;
	}

	// 反射得到bean类中的属性信息，className后期可以通过反射获取，也做到了关键字渠道
	public static List<String> getBeanName(String className) throws ClassNotFoundException {
		List<String> list = new ArrayList<String>();
		Class clazz = Class.forName(className);
		Field fields[] = clazz.getDeclaredFields();
		for (Field colum : fields) {
			colum.setAccessible(true);
			list.add(colum.getName());
		}
		return list;
	}

	// 获取param并转化[mysql]
	public static String getParamMysql(JSONObject object) {
		String param = object.getString("param");
		return param.replace("$table", object.getString("table"));
	}

	// 获取param并转化[hive]增加了tricle特有的前缀
	public static String getParamHive(JSONObject object) {
		String param = object.getString("param");
		return param.replace("$table", "wj_test_ods_view.wj_cif_" + object.getString("table"));
	}

	// 获取Table类className
	public static String getClassName(JSONObject object) {
		String channel = object.getString("channel");
		String table = object.getString("table");
		String replaceTable = table.substring(0, 1).toLowerCase() + table.substring(1);
		String className = "com.cif.trickle.channel." + channel + "." + replaceTable;
		return className;
	}

	// 获取Bean类className
	public static String getBeanClassName(JSONObject object) {
		String channel = object.getString("channel");
		String bean = object.getString("bean");
		String replaceBean = bean.substring(0, 1).toLowerCase() + bean.substring(1);
		String className = "com.cif.trickle.channel." + channel + ".bean." + replaceBean;
		return className;
	}

	public static void main(String[] args) {
		try {
			List<String> list = getBeanName("com.cif.trickle.bean.TestBean");
			for (String name : list) {
				System.out.println(name);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
