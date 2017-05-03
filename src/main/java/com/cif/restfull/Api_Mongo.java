package com.cif.restfull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.FileOperation;
import com.cif.utils.mongo.MongoOperation;
import com.cif.utils.mongo.MongoStoreEnum.KaNiu;
import com.cif.utils.mongo.MongoStoreEnum.QianZhan;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class Api_Mongo implements Runnable {
	DB mongoDb;
	DBCollection dbConn;
	String channel;
	String applyNo;
	JSONArray jsonArry = new JSONArray();
	static String fileName;
	static List<JSONArray> listJsonArrayBason;
	static long num;

	static {
		num = System.currentTimeMillis();
		fileName = Api_Mongo.class.getClassLoader().getResource("mongoBason.txt").getPath();
		listJsonArrayBason = FileOperation.readFileByLines(fileName);
	}

	// Thread run()
	public void run() {
		this.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		this.doMongo("mongo_kn", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道------------------------\n");
	}

	public static void main(String[] args) {
		Api_Mongo min = new Api_Mongo();
		min.doMongo("mongo_qz", QianZhan.class);
		System.out.println("-----------------------------钱站渠道-----------------------------\n");

		min.doMongo("mongo_kn", KaNiu.class);
		System.out.println("-----------------------------卡牛渠道------------------------\n");

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
					applyNo = insertMongo(jsonArray, num);
					jsonArrayFind = findMongoByInsert(applyNo);
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
		} else {
			throw new RuntimeException("》》》》》》枚举类型为空……");
		}
	}

	// bason插入mong，并返回新生成的applyNo
	private String insertMongo(JSONArray jsonArray, long num) {
		dbConn = MongoOperation.mongoDBConn(getClassName(jsonArray));
		JSONObject jsonRead = (JSONObject) jsonArray.get(0);
		DBObject dbo = (DBObject) com.mongodb.util.JSON.parse(jsonRead.toJSONString());
		JSONObject json = (JSONObject) JSONObject.toJSON(dbo);
		json.replace("applyNo", num + "");
		json.replace("_id", num + 1 + "");
		json.replace("idCardNum", num + 2 + "");
		json.replace("idNo", num + 2 + "");
		dbConn.insert(new BasicDBObject(json));
		return num + "";
	}

	// 根据进件号查询mongo对象，返回值类型为：JSONArray
	public JSONArray findMongoByInsert(String applyNo) {
		List<DBObject> aList = new ArrayList<DBObject>();
		BasicDBObject condition = new BasicDBObject();
		condition.put("applyNo", new BasicDBObject(QueryOperators.EXISTS, true));
		aList = dbConn.find(new BasicDBObject("applyNo", applyNo)).toArray();
		JSONArray jsonBason = JSONArray.parseArray(aList.toString());
		return jsonBason;
	}

	// 根据进件号查询mongo对象，返回值类型为：JSONArray
	public JSONArray findMongoByInsert(DBCollection dbConn, String applyNo) {
		List<DBObject> aList = new ArrayList<DBObject>();
		BasicDBObject condition = new BasicDBObject();
		condition.put("applyNo", new BasicDBObject(QueryOperators.EXISTS, true));
		aList = dbConn.find(new BasicDBObject("applyNo", applyNo)).toArray();
		JSONArray jsonBason = JSONArray.parseArray(aList.toString());
		return jsonBason;
	}

	private String getClassName(JSONArray jsonArray) {
		String className = FileOperation.getStore(jsonArray, "className");
		String[] classNames = className.split("\\.");
		return classNames[classNames.length - 1];
	}
}
