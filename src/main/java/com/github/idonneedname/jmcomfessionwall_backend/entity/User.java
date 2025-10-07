package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;

@TableName(value = "user")
@Builder
@AllArgsConstructor
public class User {
    public User()
    {}
    public User(int id,String username, String password, String name, int type, String blacklist, int pictureref) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.type = type;
        this.blacklist = blacklist;
        this.pictureref = pictureref;
    }//映射到表用的构造器
    @TableId(type= IdType.AUTO)
    public int id;
    public String username;
    @JsonIgnore
    public String password;
    public String name;
    public int type;
    @JsonIgnore
    public String blacklist;
    @JsonIgnore
    public int pictureref;//头像的id
    @TableField(exist=false)
    public  Picture portrait;//头像信息
    @JsonIgnore
    public String anonymousposts;
    @JsonProperty("anonymityList")
    @TableField(exist=false)
    public ArrayList<Integer> anonymousList;
}
