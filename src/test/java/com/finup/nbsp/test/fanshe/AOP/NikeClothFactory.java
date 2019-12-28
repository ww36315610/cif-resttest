package com.finup.nbsp.test.fanshe.AOP;

public class NikeClothFactory extends SuperFactory implements ClothFactory {

	@Override
	public void productCloth() {
		System.out.println("Nick 生产 工程");

	}

	@Override
	public void consumerCloth() {
		System.out.println("Nick 消费 工程");
	}

	public void sunMethodNike() {
		System.out.println("nike ^^^");
	}

}
