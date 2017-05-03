package com.cif.schema;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONArray;
import com.cif.utils.file.ConfigTools;
import com.cif.utils.mongo.MongoDao;
import com.cif.utils.mongo.MongoDaoImp;
import com.cif.utils.mongo.MongoOperation;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mongodb.DBCollection;
import com.mysql.jdbc.Connection;

public class CtOperation {
	static String driver;
	static String url;
	static String user;
	static String pass;
	static String filePath;
	static String channel;
	static String channelCode;
	static String xmlFile;
	private static MysqlOperation mcbp;
	private static Connection conn;
	private static MysqlDao md;
	private static Schema_Mysql smy;
	private static Schema_Mongo smo;
	private static MongoDao mgo;
	private static DBCollection dbConn;
	private static JSONArray jsonArrayMongo;
	static List<String> table_name_list;
	static {
		// channel ="redhare";
		// channelCode = "100%";
		// xmlFile ="redhare.xml";
		// channel ="qianzhan";
		// channelCode = "200%";
		// xmlFile ="qz.xml";
		// channel ="paydayloan";
		// channelCode = "300%";
		// xmlFile ="paydayloan.xml";
		channel = "nirvana";
		channelCode = "400%";
		xmlFile = "nirvana-entity.xml";
		filePath = CtOperation.class.getClassLoader().getResource(xmlFile).getPath();
		driver = ConfigTools.config.getString("mysql_cif_utcs.jdbc.dbDriver");
		url = ConfigTools.config.getString("mysql_cif_utcs.jdbc.dbUrl");
		user = ConfigTools.config.getString("mysql_cif_utcs.jdbc.username");
		pass = ConfigTools.config.getString("mysql_cif_utcs.jdbc.password");
		mcbp = MysqlOperation.getInstance(driver, url, user, pass);

		// 初始化类
		smy = new Schema_Mysql(channelCode);
		smo = new Schema_Mongo();
		mgo = new MongoDaoImp();

		// 插入mongo链接
		MongoOperation.getMongoDatabase("mongo_schema_new");
		dbConn = MongoOperation.mongoDBConn("schema");
		try {
			table_name_list = TableName_Xml.getColumn(filePath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		conn = mcbp.getConnection();
		md = new MysqlDaoImp();
	}

	public static void main(String[] args) {
		mgo.insertMongo(dbConn, getData());
	}

	public static JSONArray getData() {
		for (String table_name : table_name_list) {
			String cf_table[] = table_name.split("\\.");
			try {
				JSONArray jsonArraySql = smy.sqlJsonArray(conn, cf_table[1]);
				if (jsonArraySql.size() < 1) {
					continue;
				}
				jsonArrayMongo = smo.returnJSONArray(jsonArraySql, cf_table[0], cf_table[1], channel);
			} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return jsonArrayMongo;
	}
}
