package com.github.idonneedname.jmcomfessionwall_backend.helper.event;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import jakarta.annotation.Resource;

import java.io.Console;
import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.*;

//一个点赞事件
public class LikeEvent implements IResolvable {
    public boolean add;
    public int userId,postId;
    public EventHandler event;
    public LikeEvent(EventHandler event,boolean add,int userId,int postId) {
        this.event = event;
        this.add = add;
        this.userId = userId;
        this.postId = postId;
    }
    //处理自己的方法
    @Override
    public void Resolve() {
        Post post= Constant.postCache.tryFindById(postId);
        if(add)//表示当前是增加点赞事件
        {
            post.likes++;
            post.likelist=add(post.likelist,userId);
        }
        else
        {
            post.likes--;
            post.likelist=delete(post.likelist,userId);
        }
        Constant.postCache.tryUpdate(post);//提交更新
    }

}
