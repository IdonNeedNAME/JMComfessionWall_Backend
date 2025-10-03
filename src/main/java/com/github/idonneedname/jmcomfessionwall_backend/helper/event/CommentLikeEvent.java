package com.github.idonneedname.jmcomfessionwall_backend.helper.event;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.CommentMapper;
import jakarta.annotation.Resource;

import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.*;

public class CommentLikeEvent implements IResolvable {
    private CommentMapper commentMapper;
    public int userId;
    public Comment comment;
    public EventHandler event;
    public CommentLikeEvent(EventHandler event, int userId, Comment comment,CommentMapper commentMapper) {
        this.event = event;
        //this.add = add;
        this.userId = userId;
        this.comment = comment;
        this.commentMapper = commentMapper;
    }
    //处理自己的方法
    @Override
    public void Resolve() {
        if(idInArray(comment.likelist,userId)==-1)//表示当前是增加点赞事件
        {
            comment.likes++;
            comment.likelist=add(comment.likelist,userId);
        }
        else
        {
            comment.likes--;
            comment.likelist=delete(comment.likelist,userId);
        }
        //提交更新
        commentMapper.updateById(comment);
    }

}