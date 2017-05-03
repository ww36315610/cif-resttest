package com.cif.utils.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.FileOperation;
import com.cif.utils.jiami.JiaMi;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class MongoDao {
	// 从文件中读取bason然后插入mongo、、并返回进件号
	public static String insertMongo(DBCollection dbConn, JSONArray jsonArray, long time) {
		String className = FileOperation.getStore(jsonArray, "className");
		String[] classNameSplit = className.split("\\.");
		JSONObject jsonRead = (JSONObject) jsonArray.get(0);
		DBObject dbo = (DBObject) com.mongodb.util.JSON.parse(jsonRead.toJSONString());
		JSONObject json = (JSONObject) JSONObject.toJSON(dbo);
		json.replace("applyNo", time + "");
		json.replace("_id", time + 1 + "");
		json.replace("idCardNum", time + 2 + "");
		json.replace("idNo", time + 2 + "");
		dbConn.insert(new BasicDBObject(json));
		return time + "";
	}

	// 插入mongo
	public static void insertMongo(DBCollection dbConn, JSONArray jsonArray) {
		int num = 0;
		if (jsonArray.size() > 0) {
			for (Object object : jsonArray) {
				String _id = Math.random() * 1000000000 + num + "";
				JSONObject jsonObject = JSONObject.parseObject(object.toString());
				DBObject dbo = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toJSONString());
				JSONObject json = (JSONObject) JSONObject.toJSON(dbo);
				json.replace("_id", JiaMi.getAes(_id));
				dbConn.insert(new BasicDBObject(json));
				num++;
			}
		}
	}

	// 根据固定Key查询mongo对象，返回值类型为：JSONArray
	public static JSONArray findMongoByInsert(DBCollection dbConn, String paramKey, String paramValue) {
		List<DBObject> aList = new ArrayList<DBObject>();
		BasicDBObject condition = new BasicDBObject();
		condition.put(paramKey, new BasicDBObject(QueryOperators.EXISTS, true));
		aList = dbConn.find(new BasicDBObject(paramKey, paramValue)).toArray();
		JSONArray jsona = JSONArray.parseArray(aList.toString());
		return jsona;
	}

	// 存在查询\前几行limit，返回值类型为：JSONArray
	public static JSONArray queryByExists(DBCollection dbConn, String param, int limit) {
		JSONObject jsonObject = new JSONObject();
		List<DBObject> list = new ArrayList<DBObject>();
		JSONArray jsonArray = new JSONArray();
		BasicDBObject condition = new BasicDBObject();
		condition.put(param, new BasicDBObject(QueryOperators.EXISTS, true));
		// condition.put("idCardNum", "430502198109092516");
		try {
			System.out.println("开始查找休眠30毫秒：" + param);
			// FindIterable responseData = (FindIterable)
			// dbConn.find(condition).limit(limit);
			// MongoCursor<Document> iterator = responseData.iterator();
			// System.out.println("结束查找：" + param);
			// while (iterator.hasNext()) {
			// Document doc = iterator.next();
			// String jsonStr = doc.toJson();
			// jsonObject = JSONObject.parseObject(jsonStr);
			// list.add(jsonObject);
			// }
			// List<DBObject> aList = (List<DBObject>)
			list = dbConn.find(condition).limit(limit).toArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.parseArray(list.toString());
	}

	// 模糊查询 paramLey 包含 paramValue的数据
	public static JSONArray queryByRegex(DBCollection dbConn, String paramKey, String paramValue) {
		JSONObject jsonObject = new JSONObject();
		List<DBObject> list = new ArrayList<DBObject>();
		JSONArray jsonArray = new JSONArray();
		try {
			// 正则匹配含有paramValue参数的value
			Pattern pattern = Pattern.compile("^.*" + paramValue + ".*$");
			BasicDBObject queryObject = new BasicDBObject(paramKey, pattern);
			// query.put(param, new BasicDBObject("$regex", pattern));
			list = dbConn.find(queryObject).toArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.parseArray(list.toString());
	}
}