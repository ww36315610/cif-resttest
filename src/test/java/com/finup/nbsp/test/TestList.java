package com.finup.nbsp.test;

import com.google.common.collect.Lists;

import java.util.List;

public class TestList {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        List<String> list1 = null;
        System.out.println("list:::"+list);
        if(list !=null)
            System.out.println("1111");
        if(list1 !=null && list1.isEmpty())
        System.out.println("2222");
    }
}
