package com.cif.feater;

import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Pair<String, List<String>> pair;
    static Map<String, Object> map = new HashMap<String, Object>();

    static {
        String autoUrl = "https://api.puhuifinance.com/uaa/oauth/token?grant_type=client_credentials";
        TagsRequest tr = new TagsRequest();
        OAuth2AccessToken token = Oauth.getTokenLine(autoUrl);
        // 设置header
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        map.put("Content-Type", "application/json;charset=UTF-8");
    }

    public static void main(String[] args) {

    }




    //读取oauth
    public void getOauth(){

    }


    //获取执行方法
    public String runMethod(String methodName,String paramOne,String paramTwo){
        String returnString = null;
        Class clazz = Controller.class;
        try {
            Method method = getClass().getMethod(methodName,String.class,String.class);
            Object object =  method.invoke(this, paramOne,paramTwo);
            return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
