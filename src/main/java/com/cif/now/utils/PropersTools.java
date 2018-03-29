package com.cif.now.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.google.common.collect.Lists;

public class PropersTools {
	public static Properties propertie;
	private static FileInputStream inputFile;
	private static FileOutputStream outputFile;
	private BufferedReader reader = null;
	private static String filePath;

	static {
		// 实现子类实现按照文件顺序写入写出
		propertie = new OrderedProperties();
		filePath = PropersTools.class.getClassLoader().getResource("jdbc.properties").getPath();
//
		//启动的时候用  java -Djdbc=/path/to/jdbc.pro xxxxs -cp file.jar com.finup.Main
//		filePath =  System.getProperty("jdbc");
		 System.err.println(filePath);
		try {

			inputFile = new FileInputStream(filePath);
			// 装换成字符流，避免乱码问题
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputFile));
			propertie.load(bf);

		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
	}

	// 根据key值获得value值
	public static String getValue(String key) {
		String value = propertie.getProperty(key);// 得到某一属性的值
		// System.out.println("..................." + value);
		return value;
	}

	// 获取key值
	public static String getKey() {
		String key = null;
		Set<Object> keys = propertie.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			key = it.next().toString();
		}
		return key;
	}

	// 获取所有key值
	public static List<String> getKeys() {
		List<String> list = Lists.newArrayList();
		Set<Object> keys = propertie.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			list.add(it.next().toString());
		}
		return list;
	}

	public static void main(String[] args) {
		Set<Object> keys = propertie.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			System.err.println(getValue(it.next().toString()));
		}
	}
}
