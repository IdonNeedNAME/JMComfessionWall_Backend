package com.github.idonneedname.jmcomfessionwall_backend.helper.event;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;

import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.*;

//一个点赞事件
public class PostLikeEvent implements IResolvable {
    public int userId,postId;
    public EventHandler handler;
    public PostLikeEvent(EventHandler event, int userId, int postId) {
        this.handler = event;
        //this.add = add;
        this.userId = userId;
        this.postId = postId;
    }
    //处理自己的方法
    @Override
    public void Resolve() {
        Post post= Constant.postCache.tryFindById(postId);
        if(idInArray(post.likelist,userId)==-1)//表示当前是增加点赞事件
        {
            post.likes++;
            post.likelist=add(post.likelist,userId);
        }
        else
        {
            post.likes--;
            post.likelist=delete(post.likelist,userId);
        }
        handler.idUpdated.add(post);
        StringHelper.log(handler.idUpdated.size()+" likelists updated");
        //Constant.postCache.tryUpdate(post);//提交更新
    }

}
