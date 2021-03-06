package com.cif.restfull_jenkins;

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.file.FileOperation;
import com.cif.utils.mongo.MongoDao;
import com.cif.utils.mongo.MongoOperation;
import com.cif.utils.mongo.MongoStoreEnum.KaNiu;
import com.cif.utils.mongo.MongoStoreEnum.Nirvana;
import com.cif.utils.mongo.MongoStoreEnum.QianZhan;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MogoData {
	DB mongoDb;
	DBCollection dbConn;
	String channel;
	static long applyNo;
	JSONArray jsonArry = new JSONArray();
	static String fileName;
	static List<JSONArray> listJsonArrayBason;
	static long num;

	static {
		num = System.currentTimeMillis();
		fileName = MogoData.class.getClassLoader().getResource("mongoBason.txt").getPath();
		listJsonArrayBason = FileOperation.readFileByLines(fileName);
	}

	// Thread run()
	public void run() {
		this.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		this.doMongo("mongo_kn", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道-----------------------------\n");
	}

	public static void main(String[] args) {
		// for(int i=0;i<20;i++){
		num = System.currentTimeMillis();
		MogoData min = new MogoData();
		//
		min.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		min.doMongo("mongo_kn", KaNiu.class);
		// min.doMongo("mongo_xiaoqiang", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道-----------------------------\n");

		min.doMongo("mongo_nirvana", Nirvana.class);
		System.out.println("-----------------------------涅槃渠道-----------------------------\n");
		// }
	}

	@Test
	public void beginMongo() {
		MogoData min = new MogoData();
		min.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		min.doMongo("mongo_kn", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道-----------------------------\n");
	}

	// 调用 insert插入数据，调用find查询数据--输出find<JSONArry>
	public JSONArray doMongo(String channel, Class classEnum) {
		JSONArray jsonArrayFind = null;

		mongoDb = MongoOperation.getMongoDatabase(channel);
		for (Object object : getEnum(classEnum)) {
			dbConn = MongoOperation.mongoDBConn(object.toString());
			List<JSONArray> listJson = listJsonArrayBason;
			for (Iterator<JSONArray> iter = listJson.iterator(); iter.hasNext();) {
				JSONArray jsonArray = iter.next();
				String className = getClassName(jsonArray);
				if (className.equals(object.toString())) {
					applyNo = MongoDao.insertMongoLong(dbConn, jsonArray, num);
				} else {
					continue;
				}
			}
		}
		System.out.print("-----------------------------applyNo:" + applyNo);
		return jsonArrayFind;
	}

	// 根据不同的对象参数，获取不同类型的的数据集合
	private static Object[] getEnum(Class enumType) {
		Object[] classT = null;
		if (enumType.equals(QianZhan.class)) {
			classT = QianZhan.values();
			return classT;
		} else if (enumType.equals(KaNiu.class)) {
			classT = KaNiu.values();
			return classT;
		} else if (enumType.equals(Nirvana.class)) {
			classT = Nirvana.values();
			return classT;
		} else {
			throw new RuntimeException("》》》》》》枚举类型为空……");
		}
	}

	private String getClassName(JSONArray jsonArray) throws NullPointerException {
		String className = null;
		className = FileOperation.getStore(jsonArray, "className");
		if (className == null) {
			className = FileOperation.getStore(jsonArray, "_class");
		}

		String[] classNames = className.split("\\.");
		return classNames[classNames.length - 1];
	}
}
