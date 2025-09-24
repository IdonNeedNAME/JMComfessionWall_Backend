package com.github.idonneedname.jmcomfessionwall_backend.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringHelper {
    public static void log(int a)
    {
        log.info(String.valueOf(a));
    }//log一个数
    public static void log(String a)
    {
        log.info(a);
    }//log一个字符串
}
