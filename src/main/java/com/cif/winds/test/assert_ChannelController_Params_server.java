package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.cif.now.utils.PropersTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class assert_ChannelController_Params_server {

    private static String switchDocker = PropersTools.getValue("switch");
    private static String method = PropersTools.getValue("method");
    private Pair<String, List<String>> pair;
    static Map<String, Object> map = new HashMap<String, Object>();
    RestfulDao rd = new RestfullDaoImp();

    static {
        String autoUrl = "http://api.finupgroup.com/uaa/oauth/token?grant_type=client_credentials";
        TagsRequest tr = new TagsRequest();
        OAuth2AccessToken token = Oauth.getTokenLine();
        // 设置header
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        map.put("Content-Type", "application/json;charset=UTF-8");
    }


    // 测试-es-hbase
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
                    JSONObject jsonResult = (JSONObject) rd.getJsonArray(url, map, json).get(0);
                    System.out.println(jsonResult);
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

    public static void main(String[] args) throws Exception {
        RestfulDao rd = new RestfullDaoImp();
        assert_ChannelController_Params_server ucp = new assert_ChannelController_Params_server();
        ucp.controllerUTC(rd);
    }
}
