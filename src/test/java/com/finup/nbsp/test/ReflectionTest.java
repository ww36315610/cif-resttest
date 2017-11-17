package com.finup.nbsp.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cif.reflect.Reflection;

/**
 * 笔记：类加载的时候生成class文件 运行的时候才会把class加载进来
 * 
 * @author WangJian——20171009
 *
 */
public class ReflectionTest {
	public static void main(String[] args) {
		// 构造运行时类的Class对象
		Class clazz = Reflection.class;
		try {
			// 创建clazz对应的运行时类Reflection类的对象
			Reflection rf = (Reflection) clazz.newInstance();
			System.out.println(" 属性未通过反射调用赋值：" + rf);
			// 获取运行时类Reflection的public类型成员变量
			Field fName = clazz.getField("name");
			fName.set(rf, "刘德华");
			Field fAge = clazz.getDeclaredField("age");
			fAge.setAccessible(true);
			fAge.set(rf, 10);
			System.out.println("属性通过反射机制处理赋值后--" + rf);

			// 调用public无参方法
			Method fMethod = clazz.getMethod("method");
			fMethod.invoke(rf);
			// 调用public无参方法
			Method fMethod2 = clazz.getMethod("method", String.class);
			fMethod2.invoke(rf, "张学友");

			// 调用private无参方法
			Method pMethod = clazz.getDeclaredMethod("methodV");
			pMethod.setAccessible(true);
			pMethod.invoke(rf);
			// 调用private有参方法
			Method pMethod2 = clazz.getDeclaredMethod("methodV", String.class, int.class);
			pMethod2.setAccessible(true);
			pMethod2.invoke(rf, "郭富城", 18);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}