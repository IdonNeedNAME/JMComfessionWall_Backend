package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@TableName(value = "picture")
@Data
@Entity
@NoArgsConstructor
public class Picture {
    public Picture(byte[] pix,int width,int height){
        this.width=width;
        this.height=height;
    }
    public Picture(int width,int height,String name){
        this.width=width;
        this.height=height;
        this.name=name;
    }
    public Picture(int id,int width,int height,int featurecode,String name){
        this.id=id;
        this.width=width;
        this.height=height;
        this.featurecode=featurecode;
        this.name=name;
    }
    @TableId(type= IdType.AUTO)
    @JsonIgnore
    public int id;
    public int width;
    public int height;
    @JsonIgnore
    public int featurecode;
    @JsonIgnore
    public String name;//图片的名称
    @TableField(exist = false)
    public String url;//图片的url
}
