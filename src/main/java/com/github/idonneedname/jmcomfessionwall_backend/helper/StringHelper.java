package com.github.idonneedname.jmcomfessionwall_backend.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringHelper {
    public static String translate(int a)//数转字符串
    {
        String s="";
        while(a>0)
        {
            s=(char)(a%10+48)+s;
            a/=10;
        }
        return s;
    }
    public static void log(int a)
    {
        log.info(translate(a));
    }//log一个数
    public static void log(String a)
    {
        log.info(a);
    }//log一个字符串
}
