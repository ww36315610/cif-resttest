package com.cif.schema;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.mongo.MongoOperation;
import com.mongodb.DBCollection;

public class Schema_Mongo {

	public JSONArray returnJSONArray(JSONArray mysqlData, String cf_name, String table_name, String channel)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		MongoOperation.getMongoDatabase("mongo_schema");
		DBCollection dbConn = MongoOperation.mongoDBConn("trickleMongo");
		JSONArray josnArrayRegex = getMongoArray(dbConn, channel);
		for (Object json : josnArrayRegex) {
			JSONObject jsonObject = JSONObject.parseObject(json.toString());
			if (jsonObject.getString("key").contains(cf_name) && jsonObject.getString("key").contains(table_name)) {
				String jsonReplace = "[";
				for (Object jsonCols : JSONArray.parseArray(jsonObject.get("cols").toString())) {
					JSONObject jsonColO = JsonIteritor(JSONObject.parseObject(jsonCols.toString()), mysqlData);
					jsonReplace = jsonReplace + jsonColO;
				}
				jsonReplace = jsonReplace + "]";
				jsonObject.replace("cols", JSONArray.parse(jsonReplace));
			} else {
				continue;
			}
		}
		return josnArrayRegex;
	}

	// 反射获取mongo的数据 【静态方法】
	private JSONArray getMongoArray(DBCollection dbConn, String channel) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		Class mongoClass = Class.forName("com.cif.utils.mongo.MongoDao");
		Method staticMethod = mongoClass.getDeclaredMethod("queryByRegex", new Class[] { DBCollection.class,
				String.class, String.class });
		return (JSONArray) staticMethod.invoke(mongoClass, dbConn, "key", channel);
	}

	// 得到mysql数据，并拆分比对
	private JSONObject JsonIteritor(JSONObject mongoCompare, JSONArray mysqlCompare) {
		JSONArray arrayMongo = new JSONArray();
		for (Object ject : mysqlCompare) {
			if (mongoCompare.get("name").equals(JSONObject.parseObject(ject.toString()).get("name"))) {
				mongoCompare.remove("colType", mongoCompare.getString("colType").toString());
				mongoCompare.put("colType", JSONObject.parseObject(ject.toString()).get("colType"));
			}
		}
		return mongoCompare;
	}
}
