package com.cif.reflect;

public class Reflection {
	public String name;
	private int age;

	public Reflection() {
		System.out.println("relection 的无参-private构造函数");
	}

	private Reflection(String name, int age) {
		this.name = name;
		this.age = age;
		System.out.println("relection 的有参-public构造函数");
	}

	public void method() {
		System.out.println("public  无参 method*");
	}

	public void method(String name) {
		this.name = name;
		System.out.println("public  无参 method* name:" + name);
	}

	private void methodV() {
		System.out.println("private  无参 method*");
	}

	private void methodV(String name, int age) {
		this.name = name;
		this.age = 18;
		System.out.println("private  无参 method*name:" + name + "age:" + age);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Reflection [name=" + name + ", age=" + age + "]";
	}
}
