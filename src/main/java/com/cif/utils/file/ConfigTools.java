package com.cif.utils.file;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigTools {
	public static Config config;
	// 获取到conf 文件
	static {
		String path = ConfigTools.class.getClassLoader()
				.getResource(Contants.FILE).getPath();
//		String path = System.getProperty("oauthFile");
		Config parsedConfig = ConfigFactory.parseFile(new File(path));
		config = ConfigFactory.load(parsedConfig);
	}
}