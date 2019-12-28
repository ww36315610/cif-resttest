package com.finup.nbsp.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class TestList {
    public static void main(String[] args) {
        String jsonMap = "{lable_name=mrhao, lable_status=1, del_flag=null, update_time=2018-04-23 14:30:00.0, lable_id=1, create_time=2017-08-04 10:50:30.0, lable_type_name=test, is_mongo_sql=1, is_verify=null, channel_id=102, lable_sql=select original_file_name from \n" +
                "puhui.lend_repay_record_upload where \n" +
                "id < 100, lable_remark=测试材。。1测试材。。1测试材。。1测试材。。1测试材。。1测试材。。1}";
//        JSONObject jo = JSONObject.parseObject(josn);
//        Map<String,String> map = Maps.newHashMap();
////        map.put("aa","a");
////        map.put("bb","b");
////        map.put("cc","c");
//        map.put(j)
//        map.p
//        System.out.println(map.toString());


        String a ="{se=2016, format=xml, at=en co=3}";

        a =  a.substring(1, a.length()-1);
        StringTokenizer items;
        Map docType =Maps.newHashMap();
        for(StringTokenizer entrys = new StringTokenizer(jsonMap, ", "); entrys.hasMoreTokens();
            docType.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null)){
            items = new StringTokenizer(entrys.nextToken(), "=");
        }
        System.out.println(JSONObject.parseObject(JSON.toJSONString(docType)));

        System.out.println(testJSONArray());
    }

    public static String testJSONArray(){
        String jsonget =null;
        String j2 ="[{\"resultMap\":{\"a\":\"aa\"}},{\"resultMap\":{\"b\":\"bb\"}}]";
        JSONArray jsonArray = JSONArray.parseArray(j2);
        JSONObject json = (JSONObject) jsonArray.get(1);

        return json.toString();
    }
}
