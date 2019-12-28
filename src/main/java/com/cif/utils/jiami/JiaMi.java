package com.cif.utils.jiami;

import com.puhui.aes.AesEncryptionUtil;

public class JiaMi {
//	static String param = "xy07743844dd6ce0b97b5319eeff1c794ba6d665de045cfaf21a58a0a456693e4e20160926";
	static String param = "xy36b45e6846d8a792c9ae3e027d12ac594ae7c4af0527b4b7daa0772ad8f8c8cd20160926";
//static String param = "50038119891223941X";

	public static void main(String[] args) {

		System.out.println(param.length());
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
