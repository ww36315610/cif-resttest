package com.cif.utils.jiami;

import com.puhui.aes.AesEncryptionUtil;

public class JiaMi {
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
