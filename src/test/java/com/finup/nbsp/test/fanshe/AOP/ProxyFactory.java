package com.finup.nbsp.test.fanshe.AOP;

public class ProxyFactory implements ClothFactory {
	ClothFactory clothFactory;

	public ProxyFactory(ClothFactory clf) {
		this.clothFactory = clf;
	}

	@Override
	public void productCloth() {
		System.out.println("开始执行代理类，收代理费￥1000");
		clothFactory.productCloth();
	}

	@Override
	public void consumerCloth() {
	}
}
