package com.cif.winds.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.utils.java.MapCompareTools;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.testng.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class assert_ChannelController_Params {

    private static String switchDocker = PropersTools.getValue("switch");
    private static String method = PropersTools.getValue("method");
    private Pair<String, List<String>> pair;
    static Map<String, Object> map = new HashMap<String, Object>();
    RestfulDao rd = new RestfullDaoImp();
    private static Oauth oauth;


    static {
        String autoUrl = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
//        String autoUrl = "http://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
        TagsRequest tr = new TagsRequest();
        OAuth2AccessToken token = Oauth.getToken(autoUrl);
        // 设置header
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        map.put("Content-Type", "application/json;charset=UTF-8");
        System.out.println(map);
    }


//	static {
//		TagsRequest tr = new TagsRequest();
//		OAuth2AccessToken token = Oauth.getTokenLine();
//		// 设置header
//		map.put("Accept", "*/*");
//		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
//		map.put("Content-Type", "application/json");
//	}

    //	static {
//		TagsRequest tr = new TagsRequest();
//		OAuth2AccessToken token = Oauth.getToken();
//		// 设置header
//		map.put("Accept", "*/*");
//		map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
//		map.put("Content-Type", "application/json");
//	}
    // 打开执行开关，并执行对应case
    public void controller(RestfulDao rd) {
        String fileOut = "/Users/apple/Documents/result_1000/pre-0502.txt";
        String json = null;
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = PropersTools.getValue(switchDocker + ".address") + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                try {
                    json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
                    System.out.println(jsonResult);
                    String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"), SerializerFeature.WriteMapNullValue);
                    String slowSql = "【" + i + "】" + jsonRR + "----" + jsonResult.get("castTime");
//                    String slowSql ="【"+i+"】"+"----"+jsonResult.get("castTime");
                    System.out.println(slowSql);
                    if (Integer.parseInt(jsonResult.getString("castTime")) > 3000)
                        FileOperation.writeFile(fileOut, slowSql);
                    if (jsonResult.getInteger("failCount") > 0) {
                        Map<String, Object> map = Maps.newHashMap();
                        map = JSONObject.parseObject(json).getJSONObject("params");
                        System.err.println(jsonResult);
                        if ((jsonResult.getString("failTagReasonMap").contains("doesn't exist")) || (jsonResult.getString("failTagReasonMap").contains("does not exist"))) {
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
//						JSONObject failJson = JSONObject.parseObject(jsonResult.getString("failTagReasonMap"));
                            System.err.println("[" + i + ":" + map.get("tagName") + "]：Table Exists：：：" + jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
                            FileOperation.writeFile(fileOut, "[" + i + ":" + map.get("tagName") + "]：Table Exists：：：" + jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
                        } else {
//						System.err.println("["+i+"]"+jsonResult.getString("failTagReasonMap").substring(2,10));
                            System.out.println("[" + i + ":" + map.get("tagName") + "]：BADSQL：：" + jsonResult.getString("failTagReasonMap"));
                            FileOperation.writeFile(fileOut, "[" + i + ":" + map.get("tagName") + "]：BADSQL：：" + jsonResult.getString("failTagReasonMap"));
                        }
                    } else {
//					System.err.println("【" + i + "】" + jsonResult);
                    }
                } catch (Exception e) {
                    String ejson = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
                    FileOperation.writeFile(fileOut, "【" + i + "】:" + ejson + "--" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    // 测试凡卡-es-hbase
    public void controllerUTC(RestfulDao rd) {
        String fileOut = "/Users/apple/Documents/result_1000/pre-slow_new3900_0413.txt";
        String json = null;
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = PropersTools.getValue(switchDocker + ".address") + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                try {
                    json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
                    if (json.contains("resultId")) {
                        JSONObject jsonRe = JSONObject.parseObject(json);
                        jsonRe.remove("resultId");
                    }
//                    System.out.println("aaaaaaa::::::"+json);
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);

                    String jsonRR = JSONObject.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue);
//                    String jsonRR = JSONObject.toJSONString(jsonResult.getJSONObject("resultMap"), SerializerFeature.WriteMapNullValue);

//                    if(jsonResult.getInteger("castTime")>5000){
                        System.out.println("【" + i + "】" + jsonRR);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    // 测试凡卡-es-hbase
    public void controllerUTCGET(RestfulDao rd) {
        String fileOut = "/Users/apple/Documents/result_1000/pre-slow_new3900_0413.txt";
        String json = null;
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = PropersTools.getValue(switchDocker + ".address") + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
           System.out.println(rd.getJsonArrayGet(url,map));
        }

    }

    // 测试凡卡-es-hbase
    public void controllerUTCJ(RestfulDao rd) {
        String fileOut = "/Users/apple/Documents/result_1000/pre-slow_new3900_0413.txt";
        String json = null;
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = PropersTools.getValue(switchDocker + ".address") + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                try {
                    json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
//					System.out.println(json);
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
//					System.out.println("【"+i+"】"+jsonResult);
                    System.out.println("【" + i + "】" + JSONObject.toJSONString(jsonResult.get("resultMap"), SerializerFeature.WriteMapNullValue));
                } catch (Exception e) {
//					String ejson = JSONObject.parseObject(json).getJSONObject("params").getString("tagName");
//					FileOperation.writeFile(fileOut,"【"+i+"】:"+ejson +"--"+ e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }


    // 执行count-sum-avg函数
    public void controllerHanshu(RestfulDao rd) {
        Map<String, Object> mapResult = null;
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = PropersTools.getValue(switchDocker + ".address") + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m);
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                try {
                    String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
                    String jsonRR = JSONObject.toJSONString(jsonResult.get("resultMap"), SerializerFeature.WriteMapNullValue);
                    String tagName = JSONObject.parseObject(json).getString("tagName");
                    JSONObject asNameJSON = JSONObject.parseObject(jsonRR).getJSONArray(tagName).getJSONObject(0);
                    String slowSql = asNameJSON.getString("result");
//					String slowSql ="【"+i+"】"+asNameJSON.getString("result");
                    mapResult = JSONObject.parseObject(slowSql);
                    System.out.println(slowSql);
                    if (Integer.parseInt(jsonResult.getString("castTime")) > 3000)
                        FileOperation.writeFile("/Users/apple/Documents/result_1000/slow_new38001.txt", slowSql);
                    if (jsonResult.getInteger("failCount") > 0) {
                        Map<String, Object> map = Maps.newHashMap();
                        map = JSONObject.parseObject(json).getJSONObject("params");
                        if ((jsonResult.getString("failTagReasonMap").contains("doesn't exist")) || (jsonResult.getString("failTagReasonMap").contains("does not exist"))) {
                            System.err.println("[" + i + ":" + map.get("tagName") + "]：Table Exists：：：" + jsonResult.getString("failTagReasonMap").substring(jsonResult.getString("failTagReasonMap").lastIndexOf("Table")));
                        } else {
                            System.out.println("[" + i + ":" + map.get("tagName") + "]：BADSQL：：" + jsonResult.getString("failTagReasonMap"));
                        }
                    } else {
//						System.err.println("【" + i + "】" + jsonResult);
                    }
                } catch (Exception e) {
                    FileOperation.writeFile("/Users/apple/Documents/result_1000/slow_new38001.txt", "【" + i + "】" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }


    // 获取执行方法
    public List<String> getDoChannel() throws NullPointerException {
        List<String> listMethod = Lists.newArrayList();
        if (method.contains(",")) {
            listMethod = Arrays.asList(method.split(","));
        } else {
            listMethod.add(method);
        }
        return listMethod;
    }

    public static void main(String[] args) throws Exception {
        RestfulDao rd = new RestfullDaoImp();
        assert_ChannelController_Params ucp = new assert_ChannelController_Params();
//		ucp.controller(rd);
//		ucp.controllerHanshu(rd);
        for (int j = 0; j < 1; j++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1; i++) {
                        System.out.println("---------------------------------[[" + i + "]]-----------------------------------------");
                        ucp.controllerUTC(rd);
//                        ucp.controllerUTCGET(rd);

                    }
                }
            }).start();
        }
    }
}
