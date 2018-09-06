package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.file.FileOperation;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class assert_ChannelController_Params_fanshe {

    private static String switchDocker = PropersTools.getValue("switch");
    private static String method = PropersTools.getValue("method");
    private Pair<String, List<String>> pair;
    static Map<String, Object> map = new HashMap<String, Object>();
    RestfulDao rd = new RestfullDaoImp();
    private static Oauth oauth;


    //反射获取oauth执行方法
    static {
        oauth = new Oauth();
        String autoUrlLine = "http://api.finupgroup.com/uaa/oauth/token?grant_type=client_credentials";
        String autoUrlTest = "http://106.75.5.205:8082/uaa/oauth/token?grant_type=client_credentials";
        String autoUrl = "test".equals(switchDocker) || "beta".equals(switchDocker) || "dev".equals(switchDocker) ? autoUrlTest : autoUrlLine;
        TagsRequest tr = new TagsRequest();
        OAuth2AccessToken token = null;
        try {
            token = (OAuth2AccessToken) oauth.reflectionMethod(Oauth.getMethod(autoUrl), autoUrl);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 设置header
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        map.put("Content-Type", "application/json");
        System.out.println(map);
    }


    public static void main(String[] args) throws Exception {
        RestfulDao rd = new RestfullDaoImp();
        assert_ChannelController_Params_fanshe ucp = new assert_ChannelController_Params_fanshe();
        for (int j = 0; j < 1; j++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1; i++) {
                        System.out.println("---------------------------------[[" + i + "]]-----------------------------------------");
                        ucp.controllerUTC(rd);
                    }
                }
            }).start();
        }
    }


    // 测试接口
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
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
                    String jsonRR = JSONObject.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue);
//                    String jsonRR = JSONObject.toJSONString(jsonResult.getJSONObject("resultMap"), SerializerFeature.WriteMapNullValue);
                    System.out.println("【" + i + "】" + jsonRR);
                } catch (Exception e) {
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
}
