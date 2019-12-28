package com.finup.nbsp.test.fanshe;

import java.lang.reflect.Constructor;

public class Person_FanShe {
	public static void main(String[] args) throws ClassNotFoundException {
		// [获取Class对象]反射方式一
		Class clazz1 = Person.class;
		System.out.println("clazz1:" + clazz1);

		// [获取Class对象]反射方式二
		Class clazz2 = Class.forName("com.finup.nbsp.test.fanshe.Person");
		System.out.println("clazz2:" + clazz2);

		// [获取Class对象]反射方式三
//		Class clazz3 = new Person().getClass();
//		System.out.println("clazz3:" + clazz3);

		// 通过class对象获取对应的public构造方法
		Constructor[] constructors1 = clazz2.getConstructors();
		for (Constructor c : constructors1) {
//			System.out.println(c);
		}

		// 通过class对象获取对应的private构造方法
		Constructor[] constructors2 = clazz2.getDeclaredConstructors();
		for (Constructor c : constructors2) {
			System.out.println(c);
		}
	}
}
