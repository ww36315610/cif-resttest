package com.cif.utils.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;

import java.util.*;

public class MapCompareTools implements Comparable<MapCompareTools> {

    public String key;
    public String value;

    public MapCompareTools(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(MapCompareTools o) {
        return key.compareTo(o.key);
    }

    public static void main(String[] args) {
    }


    /**
     * 比较两个map
     *
     * @param beforeMap
     * @param afterMap
     * @return 相同返回Same map  不同返回不同key或者value
     */
    public static String compareResultOne(Map<String, Object> beforeMap, Map<String, Object> afterMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String output = new String();
        for (String key : beforeMap.keySet()) {
            Object beforeValue = beforeMap.get(key);
            Object afterValue = afterMap.get(key);
            try {
                beforeValue = null == (beforeMap.get(key)) ? "null" : beforeMap.get(key).toString().trim();
                afterValue = null == (afterMap.get(key)) ? "null" : afterMap.get(key).toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (beforeValue.equals(afterValue)) {
            } else if (afterValue == null) {
                System.err.println(1111111);
                output = output + key + " is missing, ";
                continue;
            } else {
                if (beforeValue.toString().length() != afterValue.toString().length()) {
                    output = output + "Same map-length";
                    System.err.println(222222222);
                    continue;
                }
                if (beforeValue.toString().startsWith("[{") && afterValue.toString().startsWith("[{")) {
                    System.out.println(beforeValue.toString().length() + "iiiii" + afterValue.toString().length());
                    JSONArray jsonBefor = JSONArray.parseArray(beforeValue.toString());
                    JSONArray jsonAfter = JSONArray.parseArray(afterValue.toString());
                    for (int i = 0; i < jsonBefor.size(); i++) {
                        System.out.println("9999----" + jsonAfter.getJSONObject(i));
                        compareResult(jsonBefor.getJSONObject(i), jsonAfter.getJSONObject(i));
                    }
                }
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
        System.err.println("--2--" + output);
//        output = output.substring(0, output.length() - 2);
        return output;
    }


    public static String compareResult(Map<String, Object> beforeMap, Map<String, Object> afterMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String output = new String();
        for (String key : beforeMap.keySet()) {
            Object beforeValue = beforeMap.get(key);
            Object afterValue = afterMap.get(key);
            try {
                beforeValue = null == beforeValue ? "null" : beforeValue;
                afterValue = null == afterValue ? "null" : afterValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (beforeValue.equals(afterValue)) {
//                output = "Same map";
            } else if (afterValue == null) {
                output = output + key + " is missing, ";
            } else {
                String beforeString = JSON.toJSONString(beforeValue,SerializerFeature.WriteMapNullValue);
                String afterString = JSON.toJSONString(afterValue,SerializerFeature.WriteMapNullValue);
                if (beforeString.length() == afterString.length()) {
                    if (beforeString.startsWith("[{") && afterString.startsWith("[{")) {
                       //序列化
                        JSONArray jsonBefor = JSONArray.parseArray(beforeString);
                        JSONArray jsonAfter = JSONArray.parseArray(afterString);
                        for (int i = 0; i < jsonBefor.size(); i++) {
                            compareResult(jsonBefor.getJSONObject(i), jsonAfter.getJSONObject(i));
                        }
                    }
                } else {
                    output = output + key + " has changed from " + beforeString + " to " + afterString + " , ";
                }
            }
            afterMap.remove(key);
        }
        for (String key : afterMap.keySet()) {
            output = output + key + " was added with value " + afterMap.get(key) + ", ";
        }
        if ("".equals(output) || output == null) {
            output = "Same map";
        }
        System.out.println("-------"+output);
        return output;
    }


    /**
     * 传入jsonObject，获取map并比较
     *
     * @param jsonOne
     * @param jsonTwo
     * @return
     */
    public static String compareResult(JSONObject jsonOne, JSONObject jsonTwo, String resultMap) {
        if (jsonOne.isEmpty() || jsonTwo.isEmpty()) {
            System.out.println("jsonResut 返回有为空的");
        } else {
            String jsonMapOne = JSONObject.toJSONString(jsonOne.get(resultMap), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapOne = JSONObject.parseObject(jsonMapOne);
            String jsonMapTwo = JSONObject.toJSONString(jsonTwo.get(resultMap), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapTwo = JSONObject.parseObject(jsonMapTwo);
            return compareResult(mapOne, mapTwo);
        }
        return "jsonResut 返回有为空的";
    }

    /**
     * 比较连个Set集合
     *
     * @param beforeSet
     * @param afterSet
     * @return 相同返回Same map  不同返回不同key或者value
     */

    public static String SetCompare(Set<MapCompareTools> beforeSet, Set<MapCompareTools> afterSet) {

        MapCompareTools[] beforeArray = beforeSet.toArray(new MapCompareTools[beforeSet.size()]);
        MapCompareTools[] afterArray = afterSet.toArray(new MapCompareTools[afterSet.size()]);
        String output = new String();
        output = ",";
        int beforePtr = 0;
        int afterPtr = 0;
        while (beforePtr < beforeArray.length || afterPtr < afterArray.length) {
            int difference = afterPtr >= afterArray.length ? -1 : beforePtr >= beforeArray.length ? 1 : beforeArray[beforePtr].compareTo(afterArray[afterPtr]);
            if (difference == 0) {
                if (!beforeArray[beforePtr].value.equals(afterArray[afterPtr].value)) {
                    System.out.println(beforeArray[beforePtr].key + " value changed from '" + beforeArray[beforePtr].value + "' to '" + afterArray[afterPtr].value + "'");
                    if (output.equals(",")) output = "";
                    output = output + "," + beforeArray[beforePtr].key + " value changed from '" + beforeArray[beforePtr].value + "' to '" + afterArray[afterPtr].value + "'";
                }
                beforePtr++;
                afterPtr++;
            } else if (difference < 0) {
                System.out.println(beforeArray[beforePtr].key + " is missing");
                if (output.equals(",")) {
                    output = beforeArray[beforePtr].key + " is missing";
                } else {
                    output = output + "," + beforeArray[beforePtr].key + " is missing";
                }
                beforePtr++;
            } else {
                System.out.println(afterArray[afterPtr].key + " is added");
                if (output.equals(",")) {
                    output = afterArray[afterPtr].key + " is added";
                } else {
                    output = output + "," + afterArray[afterPtr].key + " is added";
                }
                afterPtr++;
            }
        }
        System.out.println("44444" + output);
        return output;
    }


}