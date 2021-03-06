package com.cif.utils.fanshe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyChannel implements InvocationHandler {
	Object obj;// 实现了接口的被代理类的对象声明

	// ①给被代理类实例化 ②返回一个代理类的实例
	public Object build(Object obj) {
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}

	// 当通过代理类的对象发起被重写的方法的调用时，都会转化为对如下的invoke方法的调用
	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
		Object returnVal = method.invoke(obj, args);
		return returnVal;
	}
}
