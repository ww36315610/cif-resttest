package com.finup.nbsp.test.fanshe.AOP;

public class CurrencyUtils {

	public void currencyBefore() {
		System.out.println("初始化…………………………");
	}
	
	public void currencyAfter() {
		System.out.println("结束资源…………………………");
	}
}
