package com.finup.nbsp.test.fanshe.AOP;

public class SuperFactory implements ClothFactory {
	public void productCloth() {
		System.out.println("Super 生产 工程");
	}

	public void superMethod() {
		System.out.println("AAAA 生产 工程");
	}

	public void consumerCloth() {
		System.out.println("Super 消费工厂");
	}

}
