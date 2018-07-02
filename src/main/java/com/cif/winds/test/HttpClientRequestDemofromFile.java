package com.cif.winds.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.java.MapCompareTools;
import com.cif.utils.java.Oauth;
import com.cif.utils.mongo.MongoDao;
import com.cif.utils.mongo.MongoOperation;
import com.cif.utils.thread.ThreadPoolUtils;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class HttpClientRequestDemofromFile {

    private static final String switchDocker = PropersTools.getValue("switch");
    private static final String method = PropersTools.getValue("method");
    private Pair<String, List<String>> pair;
    private static Map<String, Object> header;
    private static String address;
    private RestfulDao rd;
    private SaveMongoDB smd;
    static DB db;
    static DBCollection dbConn;

    static {
        header = headerPut();
    }

    public HttpClientRequestDemofromFile() {
        rd = new RestfullDaoImp();
        db = MongoOperation.getMongoDatabase("mongo_feather_utc_rest");
        dbConn = MongoOperation.mongoDBConn(method);
    }

    public static void main(String[] args) {
        String fileOut = "/Users/apple/Documents/linlin/mongo_0515.txt";
        String fileInput = "/Users/apple/Documents/case/fanka_tidb_casepre.txt";
        HttpClientRequestDemofromFile hcrd = new HttpClientRequestDemofromFile();
        String before = "cif-utc-rest-pre";
        String after = "cif-utc-rest";


        //按照jdbc.properties的case走
//		List<String> list = PropersTools.getKeys();
        //按照读取csv的case走
        List<String> listCSV = FileOperation.readFileByLineString(fileInput);

        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                public void run() {

                    hcrd.controllerTCompareList(listCSV, before, after, fileOut);

                    //写入mongo-response-request
//					hcrd.controllerTSaveMongo(listCSV,fileOut);
                }
            }).start();
        }
    }


    //
    //

    /**
     * 打开执行开关，并执行对应case【单线程】
     */
    public void controller() {
        List<String> listMethod = getDoChannel();
        for (String m : listMethod) {
            String url = address + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                try {
                    String json = PropersTools.getValue(switchDocker + "." + m + "_" + i);
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
//					String urlReplace = url.replace();
                    System.out.println("【" + i + "】" + jsonResult);
                } catch (Exception e) {
                    System.out.println("【" + i + "】:--" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 启动线程池来执行case
     */
    public void controllerT() {
        ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = address + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                        String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
                        JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        System.out.println("【" + a + "】" + jsonResult);
                    });
                } catch (Exception e) {
                    String errorMsg = "【" + a + "】:--" + e.getMessage();
                    System.err.println(errorMsg);
                    FileOperation.writeFile("/Users/apple/Documents/linlin/error_0328.txt", errorMsg);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 启动线程池来执行case
     * 返回map比较
     */
    public void controllerTCompare(String beforeUrl, String afterUrl, String fileOut) {
        ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
        List<String> listM = getDoChannel();
        for (String m : listM) {
//			String url = address + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                        String url = address + m;
                        String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
                        JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        System.out.println("【" + a + "】" + jsonResultPre);
//						url = url.replace("cif-utc-rest-pre","cif-utc-rest");
                        url = url.replace(beforeUrl, afterUrl);
                        json = json.replace("1234", "3005");
                        JSONObject jsonResultLine = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        System.out.println("【" + a + "】line::::" + jsonResultLine);
                        if (!(jsonResultLine.getString("resultMap").contains("code#####") || jsonResultPre.getString("resultMap").contains("code#####"))) {
                            System.err.println(compareResult(jsonResultPre, jsonResultLine) == "Same map" ? "True【" + m + ":" + a + "】" : "False【" + m + ":" + a + "】" + json);
                        } else {
                            System.err.println("【" + m + ":" + a + "】" + url + "::::" + "返回code有错误的情况！！！！！！！！！！！！！！！");
                        }
                    });
                } catch (Exception e) {
                    String errorMsg = "【" + a + "】:--" + e.getMessage();
                    System.err.println(errorMsg);
                    FileOperation.writeFile(fileOut, errorMsg);
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 启动线程池来执行case
     * 返回map比较
     */
    int timePre = 0;
    int timeLine = 0;

    public void controllerTCompareList(List<String> list, String beforeUrl, String afterUrl, String fileOut) {
        ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
        List<String> listM = getDoChannel();
        for (String m : listM) {
            List<String> listKeys = list.stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                        String url = address + m;
                        String json = listKeys.get(a) == "" ? "{\"code\":\"JSON_ERR\"}" : listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=") + 1);
                        JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        url = url.replace(beforeUrl, afterUrl);
                        json = json.replace("1234","3005");
                        JSONObject jsonResultLine = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        if (!(jsonResultLine.getString("resultMap").contains("code#####") || jsonResultPre.getString("resultMap").contains("code#####"))) {
                            String jsonRR = JSONObject.toJSONString(jsonResultPre.get("resultMap"),SerializerFeature.WriteMapNullValue);
                            String jsonLL = JSONObject.toJSONString(jsonResultLine.get("resultMap"),SerializerFeature.WriteMapNullValue);
//                            System.out.println("pre::::"+jsonRR +"line::::"+jsonLL);
                            String resultAssert = compareResult(jsonResultPre, jsonResultLine) == "Same map" ? "True【" + m + ":" + a + "】" : "False【" + m + ":" + a + "】" + json;
                            int castTimePre = Integer.parseInt(jsonResultPre.getString("castTime"));
                            int castTimeLine = Integer.parseInt(jsonResultLine.getString("castTime"));
                            timePre = timePre + castTimePre;
                            timeLine = timeLine + castTimeLine;
                            if (resultAssert.contains("False")) {
                                System.out.println("----------------------------------------------");
                                System.out.println(resultAssert);
//                                String jsonRRR = JSONObject.toJSONString(jsonResultPre.get("resultMap"),SerializerFeature.WriteMapNullValue);
//                                String jsonLL = JSONObject.toJSONString(jsonResultLine.get("resultMap"),SerializerFeature.WriteMapNullValue);
                                System.err.print("pree:"+jsonRR);
                                System.err.println("line:"+jsonLL);
//                                System.out.println("pree:::" + jsonResultPre.getString("resultMap"));
//                                System.out.println("line:::" + jsonResultLine.getString("resultMap"));
                                FileOperation.writeFile(fileOut, "");
                            } else {
                                if (castTimePre > castTimeLine && castTimePre > 5000 && castTimeLine < 3000) {
                                    String resultCompare = "【" + method + ":" + json + "】::::" + "返回时间大于5秒！！！！！！！！！！！！！！！";
                                    System.err.println(resultCompare);
                                    FileOperation.writeFile(fileOut, resultCompare);
                                }
                            }
                        } else {
                            System.err.println("【" + m + ":" + a + "】" + url + "::::" + "返回code有错误的情况！！！！！！！！！！！！！！！");
                        }
                    });
                    //System.out.println("[" + a + "]:::pre=[" + timePre + "]----line=[" + timeLine + "]");
                } catch (Exception e) {
                    String errorMsg = "【" + a + "】:--" + e.getMessage();
                    System.err.println(errorMsg);
                    FileOperation.writeFile(fileOut, errorMsg);
//					FileOperation.writeFile("/Users/apple/Documents/linlin/error_0328.txt",errorMsg);
                    e.printStackTrace();
                }
            }
            System.out.println("pre=[" + timePre + "]----line=[" + timeLine + "]");
        }
    }


    /**
     * 启动线程池来执行case
     * 存储mongo
     */
    public void controllerTSaveMongo(List<String> list, String fileOut) {
        ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
        List<String> listM = getDoChannel();
        for (String m : listM) {
            List<String> listKeys = list.stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i < listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                        Long time = System.currentTimeMillis();
                        String url = address + m;
                        String json = listKeys.get(a) == "" ? "{\"code\":\"JSON_ERR\"}" : listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=") + 1);
                        JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        System.err.println(jsonResultPre);
                        if (jsonResultPre.containsKey("resultMap")) {
                            insertMongo(time + a + "", jsonResultPre.getString("castTime"), time.toString(), json, jsonResultPre.getString("resultMap"));
                        } else {
                            insertMongo(time + a + "", "", time.toString(), json, "{\"error\":\"结果未返回\"}");
                        }
                    });
                } catch (Exception e) {
//					insertMongo("id", "错误码："+switchDocker+a,"异常："+e.getMessage());
                    Long time = System.currentTimeMillis();
                    insertMongo(time + a + "", "", time.toString(), "{\"error-NO\":\"" + switchDocker + a + "\"}", "{\"error-code\":\"" + e.getMessage() + "\"}");
                    FileOperation.writeFile(fileOut, e.getMessage());
                }
            }
        }
    }


    //插入mongo
    public void insertMongo(String key, String castTime, String cteateTime, String requestBody, String responseBody) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("_id", key);
        map.put("castTime", castTime);
        map.put("cteateTime", cteateTime);
        map.put("request", JSONObject.parseObject(requestBody));
        map.put("response", JSONObject.parseObject(responseBody));
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        MongoDao.insertMongoLong(dbConn, jsonObject, Long.parseLong(key));
    }

    /**
     * 传入jsonObject，获取map并比较
     *
     * @param jsonOne
     * @param jsonTwo
     * @return
     */
    public String compareResult(JSONObject jsonOne, JSONObject jsonTwo) {
        if (jsonOne.isEmpty() || jsonTwo.isEmpty()) {
            System.out.println("jsonResut 返回有为空的");
        } else {
            String jsonMapOne = JSONObject.toJSONString(jsonOne.get("resultMap"), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapOne = JSONObject.parseObject(jsonMapOne);
            String jsonMapTwo = JSONObject.toJSONString(jsonTwo.get("resultMap"), SerializerFeature.WriteMapNullValue);
            Map<String, Object> mapTwo = JSONObject.parseObject(jsonMapTwo);
            return MapCompareTools.compareResult(mapOne, mapTwo);
        }
        return "jsonResut 返回有为空的";
    }


    public void caseMake() {
        ThreadPoolExecutor pool = ThreadPoolUtils.getThreadPoolExecutor();
        List<String> listM = getDoChannel();
        for (String m : listM) {
            String url = address + m;
            List<String> listKeys = PropersTools.getKeys().stream().filter(keysFilter -> {
                return keysFilter.contains(switchDocker + "." + m + "_");
            }).collect(Collectors.toList());
            for (int i = 1; i <= listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                        String json = PropersTools.getValue(switchDocker + "." + m + "_" + a);
                        JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                        System.out.println(switchDocker + "." + m + "_" + a + "=" + json.split("###")[0]);
//						FileOperation.writeFile("/Users/apple/Documents/linlin/case_0101.txt",json);
                    });
                } catch (Exception e) {
                    System.err.println("【" + a + "】:--" + e.getMessage());
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


    //	根据jdbc.properties文件给获取相应的oauth
    public static Map<String, Object> headerPut() {
        Map<String, Object> map = Maps.newHashMap();
        address = PropersTools.getValue(switchDocker + ".address");
        if (address.contains("cif-utc-rest")) {
            if (address.contains("api.puhuifinance.com")) {
                map = Oauth.getHeader("oauth_line_rest");
            } else {
                map = Oauth.getHeader("oauth_test_rest");
            }
        }
        if (address.contains("cif-rest-server")) {
            if (address.contains("api.puhuifinance.com")) {
                map = Oauth.getHeader("oauth_line_server");
            } else {
                map = Oauth.getHeader("oauth_test_server");
            }
        }
        return map;
    }
}
