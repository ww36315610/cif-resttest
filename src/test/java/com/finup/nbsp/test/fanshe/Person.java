package com.finup.nbsp.test.fanshe;

public class Person {
	public int age;
	public String name;
	private String address;

	public Person() {
		System.out.println("无参-public-构造器");
	}

	public Person(int age, String name) {
		this.name = name;
		this.age = age;
		System.out.println("有参-public-构造器" + name + age);
	}

	private Person(int age, String name, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
		System.out.println("有参-public-构造器" + name + age + address);
	}

	public void eat() {
		System.out.println("无参-public-方法");
	}

	public void eat(String name, int age) {
		System.out.println("有参-public-方法" + name + age);
	}

	private void show() {
		System.out.println("无参-private-方法");
	}

	private void show(String name, int age) {
		System.out.println("有参-private-方法" + name + age);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
