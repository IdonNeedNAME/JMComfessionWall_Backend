package com.github.idonneedname.jmcomfessionwall_backend;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.PostCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.PushCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.cache.SessionCache;
import com.github.idonneedname.jmcomfessionwall_backend.helper.event.EventHandler;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan("com.github.idonneedname.jmcomfessionwall_backend.mapper")
@Slf4j
@EnableTransactionManagement
public class JmComfessionWallBackendApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(JmComfessionWallBackendApplication.class, args);
        //初始化一堆缓存
        Constant.eventHandler=new EventHandler();
        Constant.postCache = new PostCache();
        Constant.postCache.postMapper = context.getBean(PostMapper.class);
        Constant.postCache.init();
        Constant.sessionCache=new SessionCache();
        Constant.pushCache=new PushCache();
        Constant.pushCache.userMapper=context.getBean(UserMapper.class);
        Constant.pushCache.resolvedPool();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        //5秒种刷新一次
        scheduler.scheduleAtFixedRate(JmComfessionWallBackendApplication::loadCache, 0, 5, TimeUnit.SECONDS);
    }
    //定时处理缓存
    public static void loadCache()
    {   //
        if(Constant.pushCache.reversible)
        {
            Constant.pushCache.reverse();
            if(Constant.pushCache.getWritePool().writable())
            {
                //直接处理数据池
                Constant.pushCache.resolvedPool();
            }
            else
            {
                //留标记待处理
                Constant.pushCache.shouldSort = true;
            }

        }
    }
}
