package com.cif;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Myclass {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        String j1 ="{\"resultMap\":{\"a\":\"aa\"}}";
        String j2 ="{\"resultMap\":{\"a\":\"aa\"}}";
        JSONObject jsonOne = JSONObject.parseObject(j1);
        JSONObject jsonTwo = JSONObject.parseObject(j2);
        System.out.println(compareJSON(jsonOne,jsonTwo));


        String jsonMap = "{lable_name=mrhao, lable_status=1, del_flag=null, update_time=2018-04-23 14:30:00.0, lable_id=1, create_time=2017-08-04 10:50:30.0, lable_type_name=test, is_mongo_sql=1, is_verify=null, channel_id=102, lable_sql=select original_file_name from \n" +
                "puhui.lend_repay_record_upload where \n" +
                "id < 100, lable_remark=测试材。。1测试材。。1测试材。。1测试材。。1测试材。。1测试材。。1}";

        System.out.println(JSONObject.parseObject(JSON.toJSONString(getMap(jsonMap))));
    }

    public static String compareJSON(JSONObject jsonOne, JSONObject jsonTwo) {
        if (jsonOne.isEmpty() || jsonTwo.isEmpty()) {
            System.out.println("jsonResut 返回有为空的");
        } else {
            String jsonMapOne = JSONObject.toJSONString(jsonOne.get("resultMap"), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapOne = JSONObject.parseObject(jsonMapOne);
            String jsonMapTwo = JSONObject.toJSONString(jsonTwo.get("resultMap"), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapTwo = JSONObject.parseObject(jsonMapTwo);
            return compareResult(mapOne, mapTwo);
        }
        return "jsonResut 返回有为空的";
    }

    public static Map getMap(String mapString) {

        StringTokenizer items;
        Map docType =Maps.newHashMap();
        for(StringTokenizer entrys = new StringTokenizer(mapString, ", "); entrys.hasMoreTokens();
            docType.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null)){
            items = new StringTokenizer(entrys.nextToken(), "=");
        }
//        System.out.println(JSONObject.parseObject(JSON.toJSONString(docType)));
        return docType;
    }


    public static String compareResult(Map<String, Object> beforeMap, Map<String, Object> afterMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String output = new String();
        for (String key : beforeMap.keySet()) {
            Object beforeValue = beforeMap.get(key);
            Object afterValue = afterMap.get(key);
            try {
                beforeValue = null == (beforeMap.get(key)) ? null : beforeMap.get(key).toString().trim();
                afterValue = null == (afterMap.get(key)) ? null : afterMap.get(key).toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (beforeValue.equals(afterValue)) {
            } else if (afterValue == null) {
                output = output + key + " is missing, ";
                continue;
            } else {
                output = output + key + " has changed from " + beforeValue + " to " + afterValue + " , ";
            }
            afterMap.remove(key);
        }
        for (String key : afterMap.keySet()) {
            output = output + key + " was added with value " + afterMap.get(key) + ", ";
        }
        if ("".equals(output) || output == null) {
            output = "Same map";
        }
        return output;
    }
}