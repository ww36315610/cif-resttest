package com.cif.utils.jiami;

import com.puhui.aes.AesEncryptionUtil;

public class JiaMi {
	static String param = "500240198906290013";

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
