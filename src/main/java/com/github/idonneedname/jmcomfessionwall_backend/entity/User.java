package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName(value ="user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 简介
     */
    private String profile;

    /**
     * 黑名单
     * （目前没有list类型）
     */
    private Integer targetId;

    /**
     * 肖像or头像
     */
    private byte[] portrait;
}