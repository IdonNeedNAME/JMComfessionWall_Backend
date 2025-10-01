package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;

import java.util.ArrayList;
//用户数据缓存
public class SessionCache {
    public InfoPool<Session> sessions;
    public SessionCache() {
        sessions = new InfoPool<>();
        sessions.pool=new ArrayList<>();
    }
    public Session userSession(int userId) {
        for(int i=0;i<sessions.pool.size();i++){
            if(userId==sessions.pool.get(i).userId){
                return sessions.pool.get(i);
            }
        }
        Session session=new Session(userId);
        sessions.pool.add(session);
        return session;
    }
}
