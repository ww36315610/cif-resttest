package com.cif;

import com.alibaba.fastjson.JSONObject;
import sun.jvm.hotspot.utilities.Assert;

public class TestJSONASSERT {


    public static void main(String[] args) {
        JSONObject jsonOne = JSONObject.parseObject("{\"resultId\":\"1dcf5b1e69f34faeb5132f3212a01607\",\"failCount\":0,\"tagIds\":null,\"message\":null,\"sync\":false,\"supplyData\":false,\"castTime\":17,\"resultMap\":{\"210202199007220019\":[{\"income\":\"6000\",\"education\":\"博士\",\"sex\":\"男\",\"jobTitle\":\"中层管理者\",\"liveProvince\":\"北京市\",\"lend_customer_basic\":[],\"email\":\"jdjdhdndj@126.com\",\"liveCity\":\"北京市\"}]},\"param\":{\"idCardNum\":\"210202199007220019\",\"channel\":\"fanxin\"},\"failTagReasonMap\":{},\"success\":true,\"successCount\":1,\"beginTime\":1556188391983,\"endTime\":1556188392000,\"successTagSet\":null,\"tagNameDebugInfoMap\":{},\"channelId\":\"3001\",\"object\":null}");
        JSONObject jsonTwo = JSONObject.parseObject("{\"resultId\":\"1dcf5b1e69f34faeb5132f3212a01607\",\"failCount\":0,\"tagIds\":null,\"message\":null,\"sync\":false,\"supplyData\":false,\"castTime\":17,\"resultMap\":{\"210202199007220019\":[{\"income\":\"6001\",\"education\":\"博士\",\"sex\":\"男\",\"jobTitle\":\"中层管理者\",\"liveProvince\":\"北京市\",\"lend_customer_basic\":[],\"email\":\"jdjdhdndj@126.com\",\"liveCity\":\"北京市\"}]},\"param\":{\"idCardNum\":\"210202199007220019\",\"channel\":\"fanxin\"},\"failTagReasonMap\":{},\"success\":true,\"successCount\":1,\"beginTime\":1556188391983,\"endTime\":1556188392000,\"successTagSet\":null,\"tagNameDebugInfoMap\":{},\"channelId\":\"3001\",\"object\":null}");
        JSONObject jsonThree= JSONObject.parseObject("{\"resultId\":\"1dcf5b1e69f34faeb5132f3212a01607\",\"failCount\":0,\"tagIds\":null,\"message\":null,\"sync\":false,\"supplyData\":false,\"castTime\":17,\"resultMap\":{\"210202199007220019\":[{\"income\":\"6000\",\"education\":\"博士\",\"sex\":\"男\",\"jobTitle\":\"中层管理者\",\"liveProvince\":\"北京市\",\"lend_customer_basic\":[],\"email\":\"jdjdhdndj@126.com\",\"liveCity\":\"北京市\"}]},\"param\":{\"idCardNum\":\"210202199007220019\",\"channel\":\"fanxin\"},\"failTagReasonMap\":{},\"success\":true,\"successCount\":1,\"beginTime\":1556188391983,\"endTime\":1556188392000,\"successTagSet\":null,\"tagNameDebugInfoMap\":{},\"channelId\":\"3001\",\"object\":null}");

        org.testng.Assert.assertEquals(jsonOne,jsonThree,"aaaaa");
    }
}
