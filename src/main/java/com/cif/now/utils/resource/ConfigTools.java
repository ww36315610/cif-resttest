package com.cif.now.utils.resource;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigTools {
	public static Config config;
	// 获取到conf 文件
	static {
		String path = ConfigTools.class.getClassLoader().getResource(Contants.FILE).getPath();
		// liunx服务器作为-Dconf='conf/app.properties'读取服务器文件
		// String path = System.getProperty("conf");
		Config parsedConfig = ConfigFactory.parseFile(new File(path));
		config = ConfigFactory.load(parsedConfig);
	}
}