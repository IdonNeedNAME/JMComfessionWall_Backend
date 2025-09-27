package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@TableName(value = "comment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    public Comment(int id, int host, String content, int depth, boolean hidden, String likelist, String subcomment) {
        this.id = id;
        this.host = host;
        this.content = content;
        this.depth = depth;
        this.hidden = hidden;
        this.likelist = likelist;
        this.subcomment = subcomment;
    }

    @TableId(type = IdType.AUTO)
    public int id;
    public int host;
    public String content;
    public int depth;
    public boolean hidden;


    //下面两个实质是个存放id的List，用ArrayNodeHelper里的方法操作
    @JsonIgnore
    public String likelist;//点赞人
    @JsonIgnore
    public String subcomment;//子评论

    @TableField(exist = false)
    public List<Comment> subcomments;
    @TableField(exist = false)
    public boolean liked;
    @TableField(exist = false)
    public int likes;
    @TableField(exist = false)
    public int commentCount;

    public void setContent(String content) {
        if(content==null||content.isEmpty()) {throw new ApiException(ExceptionEnum.NULL_CONTENT);}
        if(content.length()>1000) {throw new ApiException(ExceptionEnum.CONTENT_TOO_LONG);}
        this.content = content;
    }
}

