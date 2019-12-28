package com.finup.nbsp.test.fanshe.AOP;

public class AdidasClothFactory extends SuperFactory implements ClothFactory {

	@Override
	public void productCloth() {
		System.out.println("Adidas 生产 工程");
	}

	@Override
	public void consumerCloth() {
		System.out.println("Adidas 消费工程");
	}

	public void sunMethodAdidas() {
		System.out.println("adidas ^^^");
	}
}
