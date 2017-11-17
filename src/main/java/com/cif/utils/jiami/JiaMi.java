package com.cif.utils.jiami;

import com.puhui.aes.AesEncryptionUtil;

public class JiaMi {
	static String param = "xy3d216103d9c080fe7da3958305d70c827dec04dedcbd31ad7af719042516f51020160926";

	public static void main(String[] args) {
		System.out.println(getAes(param));
	}

	// 加密
	public static String getAes(String paramValue) {
		String xy = null;
		if (paramValue.startsWith("xy")) {
			xy = AesEncryptionUtil.decrypt(paramValue);
		} else {
			xy = AesEncryptionUtil.encrypt(paramValue);

		}
		return xy;
	}
}
