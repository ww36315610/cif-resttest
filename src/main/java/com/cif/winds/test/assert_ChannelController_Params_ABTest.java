package com.cif.winds.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cif.now.utils.PropersTools;
import com.cif.utils.httpclient.Oauth;
import com.cif.winds.beans.TagsRequest;
import com.cif.winds.repository.RestfulDao;
import com.cif.winds.repository.RestfullDaoImp;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class assert_ChannelController_Params_ABTest {

    private static String switchDocker = PropersTools.getValue("switch");
    private static String method = PropersTools.getValue("method");
    private Pair<String, List<String>> pair;
    static Map<String, Object> map = new HashMap<String, Object>();
    RestfulDao rd = new RestfullDaoImp();
    private static Oauth oauth;

    //反射获取oauth执行方法
    static {
        oauth = new Oauth();

        OAuth2AccessToken token  = (OAuth2AccessToken) getTokenTest("");
//        OAuth2AccessToken token  = (OAuth2AccessToken) getTokenLine("");
        // 设置header
        map.put("Accept", "*/*");
        map.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        map.put("Content-Type", "application/json");
        System.out.println(map);
    }


    public static OAuth2AccessToken getTokenTest(String url) {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri("http://t.uaa.pub.puhuifinance.com:8082/uaa/oauth/token?grant_type=client_credentials");
        resource.setClientId("strategicengine-WZWGYhuj");
        resource.setClientSecret("Sf5ayZvwF4rT");
        resource.setGrantType("client_credentials");
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return accessToken;
    }

    public static OAuth2AccessToken getTokenLine(String url) {
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAccessTokenUri("https://api.finupgroup.com/uaa/oauth/token?grant_type=client_credentials");
        resource.setClientId("fpx-recos-abtest");
        resource.setClientSecret("Wm5CNExYSmxZMjl6TFdGaWRHVnpkQSUzRCUzRA==");
        resource.setGrantType("client_credentials");
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());

        System.out.println("------------"+accessToken.getValue());
        return accessToken;
    }

//    http://fpx-recos-abtest.strategicengine.production/api/v1


    // 测试接口
    public void controllerUTC(RestfulDao rd) {
        //LINE
//        String url = "http://fpx-recos-abtest.strategicengine.production/api/v1/abtest?roject_code=phone_rank&unique_key=220382199012202228&uniqueKeyType";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtestWithWhiteList?project_code=phone_rank&unique_key=220382199012202228&uniqueKeyType";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtestWithWhiteList?project_code=dig-ff-abTest&unique_key=220382199012202228&uniqueKeyType";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtestWithWhiteList?project_code=dig-ff-abTest&unique_key=220382199012202228&uniqueKeyType=IDNO";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=220382199012202232";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=15015794282";
        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=13207471428";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=15956626296";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=15973582434";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=15564596000";
//        String url ="http://fpx-recos-abtest.strategicengine.test/api/v1/abtest?project_code=dig-ff-abTest&unique_key=15608428221";








        String json = "";
        JSONObject jsonResult = (JSONObject) rd.getJsonArrayGet(url, map).get(0);
        String jsonRR = JSONObject.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue);
        System.out.println(jsonRR);


    }

    public static void main(String[] args) {
        RestfulDao rd = new RestfullDaoImp();
        assert_ChannelController_Params_ABTest aa = new assert_ChannelController_Params_ABTest();
        aa.controllerUTC(rd);

    }
}