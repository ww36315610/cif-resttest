package com.finup.nbsp.test;


import java.util.HashMap;
import java.util.Map;

public class Myclass
{
    public String compareResult() {
        Map<String, String> resultMap = new HashMap<String, String>();
        String output = new String();
//        for (String key : beforeMap.keySet()) {
//            Object beforeValue = beforeMap.get(key);
//            Object afterValue = afterMap.get(key);
//            try {
//                beforeValue = null==(beforeMap.get(key)) ? null : beforeMap.get(key).toString().trim();
//                afterValue = null==(afterMap.get(key)) ? null : afterMap.get(key).toString().trim();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            if (beforeValue.equals(afterValue)) {
//            } else if (afterValue == null) {
//                output = output + key + " is missing, ";
//                continue;
//            } else {
//                output = output + key + " has changed from " + beforeValue + " to " + afterValue + " , ";
//            }
//            afterMap.remove(key);
//        }
//        for (String key : afterMap.keySet()) {
//            output = output + key + " was added with value " + afterMap.get(key) + ", ";
//        }
//        if ("".equals(output)||output == null) {
//            output = "Same map";
//        }
        return output;
    }
    public int add(int a, int b)
    {
        return a + b;
    }    
}