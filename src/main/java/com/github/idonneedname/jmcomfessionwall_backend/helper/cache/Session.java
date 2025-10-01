package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;

import java.util.ArrayList;
import java.util.List;
//用户数据（部分）
public class Session {
    public int userId;
    public List<Integer> hadVisited;//已获取过的帖子
    public Session(int userId){
        this.userId=userId;
        this.hadVisited=new ArrayList<Integer>();
    }
    public boolean userHasVisited(int postId) {
        for(int j=0;j<this.hadVisited.size();j++){
            if(this.hadVisited.get(j)==postId){
                return true;
            }
        }
        return false;
    }
}
