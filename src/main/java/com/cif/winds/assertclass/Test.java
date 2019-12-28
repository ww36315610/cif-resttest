package com.cif.winds.assertclass;

import java.util.ArrayList;
import java.util.List;

public class Test {


    private static List<String> trimSpace(String[] arr) {
        List<String> list = new ArrayList<>();
        for (String a : arr) {
            if (a.length() > 0) {
                list.add(a);
            }
        }
        return list;
    }

    private static String de_key(String sql) {
        String key;
        String selectFields = sql.substring(0, sql.lastIndexOf("from")).replaceAll("\n", "")
                .replaceAll("select", "").replaceAll("high_priority", "");
        String[] fields = selectFields.split(",");
        String keyString = "";

        for (String f : fields) {
            if (f.contains("decrypt_string")) {
                keyString = f;
                break;
            }
        }

        if (keyString.contains("as")) { // decrypt_string(xx)     as    xx
            List<String> list = trimSpace(keyString.trim().split(" "));
            key = list.get(2);
        } else if (keyString.trim().contains(" ")) { // decrypt_string(xx)    xx
            List<String> list = trimSpace(keyString.trim().split(" "));
            key = list.get(1);
        } else {   // decrypt_string(xx)
            key = keyString.trim();
        }

        return key;
    }


    public static void main(String[] args) {
        String sql = "select high_priority case when decrypt_string(uu.id_card) is not null then 1 else 0 end as exist\n" +
                "-- ,decrypt_string(uu.phone) as phone\n" +
                "from `loan-center-prod`.user uu\n" +
                "where uu.id_card = '$$id_no'";

        long start = System.currentTimeMillis();
        de_key(sql);
        long cast = System.currentTimeMillis() - start;
        System.out.println(cast);
    }

}
