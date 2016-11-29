package pu.hui.mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pu.hui.mybatis.dao.IDataStatusDao;
import pu.hui.mybatis.entity.DataStatusEntity;

public class TestMybatis {

	public static void main(String[] args) {

		ApplicationContext ctx=null;
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		IDataStatusDao userDao=(IDataStatusDao) ctx.getBean("idataStatusDao");
		
		DataStatusEntity ds = new DataStatusEntity();
		ds.setType(8);
		DataStatusEntity ds2 = userDao.getDataStatus(ds);
		System.out.println(ds2.getUid());
	}

}
