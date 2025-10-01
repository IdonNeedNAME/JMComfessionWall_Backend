package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@TableName(value = "post")
@Data
@Builder
@AllArgsConstructor
public class Post {
    public Post()
    {}
    public Post(int id,int host,String title,
    String content,int depth,boolean anonymity,boolean hidden,boolean isPublic,
    String likelist,String picture,String subcomment,int comments,int likes)
    {
        this.id = id;
        this.host=host;
        this.title=title;
        this.content=content;
        this.depth=depth;
        this.anonymity=anonymity;
        this.hidden=hidden;
        this.ispublic=isPublic;
        this.likelist=likelist;
        this.picture=picture;
        this.subcomment=subcomment;
        this.likes=likes;
        this.comments=comments;
    }

    @TableId(type= IdType.AUTO)
    public int id;
    public int host;
    @JsonIgnore
    public long date;
    public String title;
    public String content;
    public int depth;
    @JsonIgnore
    public boolean anonymity;
    @TableLogic
    public boolean hidden;
    @JsonIgnore
    public boolean ispublic;
    //下面三个实质是个存放id的List，用ArrayNodeHelper里的方法操作
    @JsonIgnore
    public String likelist;//点赞人列表
    @JsonIgnore
    public String picture;//图片列表
    @JsonIgnore
    public String subcomment;//子评论
    public int likes;
    public int comments;


    @TableField(exist = false)
    public String hostname;
    @TableField(exist = false)
    public Picture hostportrait;
    @TableField(exist = false)
    public ArrayList<Comment> subcomments;
    @TableField(exist = false)
    public ArrayList<Picture> pictures;
    @TableField(exist = false)
    public boolean liked;

}
