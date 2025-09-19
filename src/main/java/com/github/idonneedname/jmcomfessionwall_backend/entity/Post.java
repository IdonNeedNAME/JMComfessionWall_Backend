package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import lombok.Data;


@TableName(value ="post")
@Data
public class Post {
    /**
     * post次序
     */
    @TableId(type = IdType.AUTO)
    private Integer postId;

    /**
     * 标题
     */
    private String title;

    /**
     * post内容
     */
    private String content;

    /**
     * 创建者的账号索引
     */
    private Integer userId;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}