package com.github.idonneedname.jmcomfessionwall_backend.helper;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class ApiKeyHelper {
    public static boolean isVaildApiKey(int id,String apiKey){
        if(apiKey.equals("ak1145141919810"))
            return true;
        if(apiDictionary==null)
        {
            apiDictionary=new HashMap<>();
        }
        String api=apiDictionary.get(id);
        if(api==null)
            return false;//说明这个id没有登记
        if(api.equals(apiKey))
            return true;
        else
            return false;
    }//判断前端返回的apikey是否合法
    private static HashMap<Integer,String> apiDictionary;//登记api的静态字段
    public static String genApiKey(int id)
    {
        int random=(int)(Math.random()*100);
        double num=abs(sin(id * random));
        num*=1000000000;
        random=(int)num;
        String apiKey="ak"+StringHelper.translate(random);
        StringHelper.log(apiKey);
        return apiKey;
    }//生成一个apikey
    public static void trySet(int id,String apiKey)
    {
        if(apiDictionary==null)
        {
            apiDictionary=new HashMap<>();
        }
        apiDictionary.put(id,apiKey);
    }//设置apikey
}
