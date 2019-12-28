package com.cif.winds.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.mongo.MongoDao;
import com.cif.utils.mongo.MongoOperation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

public class SaveMongoDB {
    private MongoOperation mongoTools;
    private MongoDao mgDao;
    static DB db;
    static DBCollection dbConn;
    public SaveMongoDB(DB dbA,DBCollection dbConnA){
        this.db =dbA;
        this.dbConn = dbConnA;
    }

    public JSONObject insertMongo(){
         String json1 = "{\"aaaa\":\"AAAA\",\"cccc\":{\"ccc\":\"CCC\",\"cc\":\"CC\"}}";
         String json2 = "{\"bbbb\":\"BBBB\"}";
         String key  = "111id";
         Map<String,Object> map = Maps.newHashMap();
         map.put("_id",key);
         map.put("request",JSONObject.parseObject(json1));
         map.put("response",JSONObject.parseObject(json2));
//         map.put("request",json1);
//         map.put("response",json2);
         JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
         System.out.println(jsonObject);
         return jsonObject;
    }

    private List<String> getDate(){
        String fileCSV = "/Users/apple/Downloads/graylog-search-result-absolute-2018-04-17T00_00_00.000Z-2018-04-17T03_00_00.000Z.csv";
        List<String> listCSV = CsvReadTools.getCaseFromCSV(fileCSV);
        List<String> listRequestBody = Lists.newArrayList();
        listCSV.forEach(l->{
            System.out.println(l);
            listRequestBody.add(l.split("=")[1]);
        });
        return listRequestBody;
    }


    //插入mongo
    public void insertMongo(String key,String requestBody,String responseBody) throws Exception{
        Long time = System.currentTimeMillis();
        Map<String,Object> map = Maps.newHashMap();
        map.put("_id",key);
        map.put("request",JSONObject.parseObject(requestBody));
        map.put("response",JSONObject.parseObject(responseBody));
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        MongoDao.insertMongoLong(dbConn,jsonObject,time);
    }

    public static void main(String[] args) {
        SaveMongoDB smd = new SaveMongoDB(MongoOperation.getMongoDatabase("mongo_qz"),MongoOperation.mongoDBConn("ARQPRE"));
        List<String> list =  smd.getDate();
        try {
            for(int i=1;i<list.size();i++){
                smd.insertMongo("id",list.get(i),list.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
