package pu.hui.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropUtil {
	private static Properties properties = null;
	private static Logger logger = Logger.getLogger(PropUtil.class);

	public PropUtil(String path) {
		initialize(path);
	}

	private void initialize(String path) {
		InputStream is = PropUtil.class.getClassLoader().getResourceAsStream(
				path);
		if (is == null) {
			logger.error("The properties is null.");
			return;
		}
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			logger.error("ִ��PropUtil.initialize()���������쳣���쳣��Ϣ��", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				logger.error("ִ��PropUtil.initialize()���������쳣���쳣��Ϣ��", e);
			}
		}
	}

	/**
	 * get specified key in config files
	 * 
	 * @param key
	 *            the key name to get value
	 */
	public String get(String key) {
		String keyValue = null;
		if (properties.containsKey(key)) {
			keyValue = (String) properties.get(key);
		}
		return keyValue;
	}
}
