package com.finup.nbsp.test.fanshe.AOP;

public class Contorller {
	public static void main(String[] args) {
		// NikeClothFactory ncf = new NikeClothFactory();
		// ProxyFactory pxy = new ProxyFactory(ncf);
		// pxy.productCloth();
		try {
			testDongTai();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void testDongTai() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 1.被代理类的对象 //此处可以使用反射技术获取到 ClothFactoru factory =
		// clss.forName("Nike");
		Class classNike = NikeClothFactory.class;
		Class classAdidas = Class.forName("com.finup.nbsp.test.fanshe.AOP.AdidasClothFactory");
		// 2.创建一个实现了InvacationHandler接口的类的对象
		DongTaiDaiLi handler = new DongTaiDaiLi();
		// 3.调用blind()方法，动态的返回一个同样实现了Nike类实现的接口ClothFactory的代理类的对象
		ClothFactory cf = (ClothFactory) handler.blind(classNike.newInstance());// 此时cf就是代理类的对象
		cf.productCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		cf.consumerCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		ClothFactory cf1 = (ClothFactory) handler.blind(classAdidas.newInstance());// 此时cf就是代理类的对象
		cf1.productCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		cf1.consumerCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		
		String 	className = "com.finup.nbsp.test.fanshe.AOP.AdidasClothFactory";
		ClothFactory cfobj = returnObject(className);// 此时cf就是代理类的对象
		cfobj.productCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		cfobj.consumerCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用

	}
	
	public void testDongTai(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 1.被代理类的对象 [className可以放入case中作为关键字传入，构成关键字驱动]
		className = "com.finup.nbsp.test.fanshe.AOP.AdidasClothFactory";
		Class clazz = Class.forName(className);
		// 2.创建一个实现了InvacationHandler接口的类的对象[动态代理]
		DongTaiDaiLi handler = new DongTaiDaiLi();
		// 3.调用blind()方法，动态的返回一个同样实现了Adidas类实现的接口ClothFactory的代理类的对象
		ClothFactory cf = (ClothFactory) handler.blind(clazz.newInstance());// 此时cf就是代理类的对象
		cf.productCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
		cf.consumerCloth();// 转到对InvacationHandler接口的实现类的invoke()方法的调用
	}
	
	public static ClothFactory returnObject(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 1.被代理类的对象 [className可以放入case中作为关键字传入，构成关键字驱动]
		Class clazz = Class.forName(className);
		DongTaiDaiLi handler = new DongTaiDaiLi();
		return (ClothFactory) handler.blind(clazz.newInstance());
	}
}
