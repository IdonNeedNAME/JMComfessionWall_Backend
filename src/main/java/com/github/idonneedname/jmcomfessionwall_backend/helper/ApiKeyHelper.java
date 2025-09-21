package com.github.idonneedname.jmcomfessionwall_backend.helper;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class ApiKeyHelper {
    public static boolean isVaildApiKey(int id,String apiKey){
        return true;
    }
    private static HashMap<Integer,String> apiDictionary;
    public static String genApiKey(int id)
    {
        int random=(int)(Math.random()*100);
        double num=abs(sin(id * random));
        num*=1000000000;
        random=(int)num;
        return "ak"+StringHelper.translate(random);
    }
    public static void trySet(int id,String apiKey)
    {
        if(apiDictionary==null)
        {
            apiDictionary=new HashMap<>();
        }
        apiDictionary.put(id,apiKey);
    }
}
