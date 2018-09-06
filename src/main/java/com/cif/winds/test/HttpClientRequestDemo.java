package com.cif.winds.test;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.CsvReadTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.java.MapCompareTools;
import com.cif.utils.java.Oauth;
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

public class HttpClientRequestDemo {

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

    public HttpClientRequestDemo() {
        rd = new RestfullDaoImp();
        db = MongoOperation.getMongoDatabase("mongo_feather_utc_rest");
        dbConn = MongoOperation.mongoDBConn(method);
    }

    public static void main(String[] args) throws InterruptedException {
        String fileOut = "_0711";
//        String fileCSV = "/Users/apple/Downloads/graylog-search-result-absolute-2018-04-17T00_00_00.000Z-2018-04-17T03_00_00.000Z.csv";

        //生产最新的log日志，量大  -测试所有接口
//        String fileCSV = "/Users/apple/Downloads/graylog-all_0628.csv";

        //生产最新的log日志，量大  -测试所有接口
        String fileCSV = "/Users/apple/Downloads/graylog-search-result-relative-7200.csv";
//
        //测试Tidb--method=oneTagName 凡卡标签
//        String fileCSV = "/Users/apple/Downloads/graylog-tidb_0625_samll.csv";
//        String fileCSV = "/Users/apple/Downloads/graylog-tidb_0625_big.csv";

        // 测试moxie一个小时量
//        String fileCSV = "/Users/apple/Downloads/graylog-search-result-relative-3600.csv";


        //测试butterfly-sort专用-method=moxie
//        String fileCSV = "/Users/apple/Downloads/graylog-moxie_0626.csv";

        HttpClientRequestDemo hcrd = new HttpClientRequestDemo();

        String before = "api.finupgroup.com/cif-utc-rest-pre";
        String after = "api.finupgroup.com/cif-utc-rest";
        //按照jdbc.properties的case走
//		List<String> list = PropersTools.getKeys();

        //按照读取csv的case走
        List<String> listCSV = CsvReadTools.getCaseFromCSV(fileCSV);

        //按照文件读取case走
//        String fileCase = "/Users/apple/Documents/case/caseMoxie.txt";


        String fileCrawler = "/Users/apple/Documents/case/crawler.txt";
        String fileCase = "/Users/apple/Documents/case/moxieslow.txt";
        String fileCase1 = "/Users/apple/Documents/case/moxieslow_moxie.txt";
        String fileCase2 = "/Users/apple/Documents/case/compare.txt";
        String fileCase3 = "/Users/apple/Documents/case/one.txt";
        String fileCase4 = "/Users/apple/Documents/case/lnull.txt";
//        List<String> listFile = FileOperation.readFileByLineString(fileCase4);


        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                public void run() {
                    //从excell日志文件读取执行case
                    hcrd.controllerTCompareList(listCSV, before, after, fileOut);

                    //从txt文件外部读取执行case
//                    hcrd.controllerTCompareList(listFile, before, after, fileOut);

                    //写入mongo-response-request
//					hcrd.controllerTSaveMongo(listCSV,fileOut);
                }
            }).start();
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
            for (int i = 1; i < listKeys.size(); i++) {
                final int a = i;
                try {
                    pool.execute(() -> {
                    String url = address + m;
                    String urlP = address + m;
                    String json = listKeys.get(a) == "" ? "{\"code\":\"JSON_ERR\"}" : listKeys.get(a).toString().substring(listKeys.get(a).toString().indexOf("=") + 1);
//                        json = json.replace("3005","9988");
//                        System.out.println("json:::::"+json);
                    if (json.contains("resultId")) {
                        JSONObject jsonRe = JSONObject.parseObject(json);
                        jsonRe.remove("resultId");
                    }
                    JSONObject jsonResultPre = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                    url = url.replace(beforeUrl, afterUrl);
                    JSONObject jsonResultLine = (JSONObject) rd.getJsonArray(url, header, json).get(0);
                    if (jsonResultPre.size() > 0 && jsonResultLine.size() > 0) {
                           if(jsonResultPre.size() !=jsonResultLine.size()){
                               System.out.println("--[序列化]--"+json);
                               System.out.println("pree:::" + jsonResultPre.size());
                               System.out.println("line:::" + jsonResultLine.size());
                           }else if (jsonResultLine.getString("resultMap").contains("code#####")) {
                                String fileOut1 = "/Users/apple/Documents/linlin/" + m+fileOut + "_Linecode1.txt";
                                FileOperation.writeFile(fileOut1, json);
                            }
                        if (jsonResultPre.getString("resultMap").contains("code#####")) {
                            String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_Precode2.txt";
                            FileOperation.writeFile(fileOut1, json);
                        }
                        if (!(jsonResultLine.getString("resultMap").contains("code#####") || jsonResultPre.getString("resultMap").contains("code#####"))) {
                            String comResult = compareResult(jsonResultLine, jsonResultPre);
                            String resultAssert = comResult == "Same map" ? "True【" + m + ":" + a + "】" : "False【" + m + ":" + a + "】" + json;
                            int castTimePre = Integer.parseInt(jsonResultPre.getString("castTime"));
                            int castTimeLine = Integer.parseInt(jsonResultLine.getString("castTime"));
                            timePre = timePre + castTimePre;
                            timeLine = timeLine + castTimeLine;
//                            if (resultAssert.contains("False")&&!comResult.equals("Same map-length")&&!m.equals("multiTagName")) {
                            if (resultAssert.contains("False")) {
                                System.out.println("----------------------------------------------");
                                System.out.println(resultAssert);
                                String jsonMapPre = JSONObject.toJSONString(jsonResultPre.get("resultMap"), SerializerFeature.WriteMapNullValue);
                                String jsonMapLine = JSONObject.toJSONString(jsonResultLine.get("resultMap"), SerializerFeature.WriteMapNullValue);
                                System.out.println("pree:::" + jsonMapPre);
                                System.out.println("Line:::" + jsonMapLine);
                                if (jsonResultPre.getInteger("failCount") > 0) {
                                    String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_PreFaile3.txt";
                                    FileOperation.writeFile(fileOut1, resultAssert);
                                } else if (jsonResultLine.getInteger("failCount") > 0) {
                                    String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_LineFaile4.txt";
                                    FileOperation.writeFile(fileOut1, resultAssert);
                                } else {
                                    String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_compare5.txt";
                                    FileOperation.writeFile(fileOut1, resultAssert);
                                }
                            } else {
                                if (castTimePre > castTimeLine && castTimePre > 5000 && castTimeLine < 3000) {
                                    String resultCompare = "【" + method + ":" + json + "】::::" + "PPRREE返回时间大于5秒！！！！！！！！！！！！！！！";
//                                    System.err.println(resultCompare);
                                    String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_time6.txt";
                                    FileOperation.writeFile(fileOut1, resultCompare);
                                }
                                if (castTimePre < castTimeLine && castTimePre < 3000 && castTimeLine > 5000) {
                                    String resultCompare = "【" + method + ":" + json + "】::::" + "LLIINN返回时间大于5秒！！！！！！！！！！！！！！！";
                                    System.err.println(resultCompare);
                                    String fileOut1 = "/Users/apple/Documents/linlin/" + m + fileOut + "_time_line6.txt";
                                    FileOperation.writeFile(fileOut1, resultCompare);
                                }
                                if (castTimePre > 15000) {
                                    String fileOut2 = "/Users/apple/Documents/linlin/" + m + fileOut + "_Bigtime7.txt";
                                    FileOperation.writeFile(fileOut2, "PPRREE::::【" + castTimePre + "】" + json);
                                } else if (castTimeLine > 10000) {
                                    String fileOut2 = "/Users/apple/Documents/linlin/" + m + fileOut + "_Bigtime_line7.txt";
                                    FileOperation.writeFile(fileOut2, "LLIINN::::【" + castTimeLine + "】" + json);
                                }
                            }
                        } else {
                            System.err.println("【" + m + ":" + a + "】" + json + url + "::::" + "返回code有错误的情况！！！！！！！！！！！！！！！");
                        }
                    } else {
                        System.err.println("【" + m + ":" + a + "】" + json + "::::" + "返回值为null");
                    }
                    });
                    System.out.println("[" + a + "]:::pre=[" + timePre + "]----line=[" + timeLine + "]");
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
            if (jsonMapOne.length() != jsonMapTwo.length()) {
//                return "Same map-length";
            }
            return MapCompareTools.compareResult(mapOne, mapTwo);
        }
        return "jsonResut 返回有为空的";
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
//            if (address.contains("api.puhuifinance.com")) {
            if (address.contains("api.finupgroup.com")) {
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
