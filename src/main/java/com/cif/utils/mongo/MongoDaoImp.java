package com.cif.utils.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCollection;

public class MongoDaoImp extends MongoDao {

	public static void main(String[] args) {
		MongoOperation.getMongoDatabase("mongo_schema");
		DBCollection dbConn = MongoOperation.mongoDBConn("trickleMongo");
		JSONArray josnArrayRegex = queryByRegex(dbConn, "key", "nirvana");
		System.out.println(josnArrayRegex.size());
		for (Object json : josnArrayRegex) {
			JSONObject jsonObject = JSONObject.parseObject(json.toString());
			System.out.println(jsonObject.get("cols"));
		}
	}

	public JSONObject JsonIteritor(JSONArray jsonArray) {
		JSONObject json = null;
		for (Object ject : jsonArray) {
			json = JSONObject.parseObject(ject.toString());
			if (json.get("name").equals("mysqlName")) {
				// json.get("colType").toString().replace(, "mysqlType")
				json.get("colType");
			}
		}
		return json;
	}
}
