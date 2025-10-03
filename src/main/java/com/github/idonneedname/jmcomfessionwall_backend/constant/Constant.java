package com.github.idonneedname.jmcomfessionwall_backend.constant;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.PostCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.PushCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.SessionCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.event.EventHandler;

public class Constant {
    //改成你自己的
    public static String basePath = "C:\\Users\\xxx\\Desktop\\xiangmu\\JMComfessionWall_Backend\\uploads\\images\\";
    public static String baseUrl = "http://127.0.0.1:8080/images/";
    public static Picture defaultPicture = new Picture(322,322,"default.png");
    public static PostCache postCache ;
    public static SessionCache sessionCache ;
    public static PushCache  pushCache ;
    public static EventHandler  eventHandler;

}
