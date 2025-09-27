package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.ArrayList;

@TableName(value = "picture")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Picture {
    public Picture(){}
    public Picture(byte[] pix,int width,int height){
        this.width=width;
        this.height=height;
        this.pixels=pix;
    }
    public Picture(int id,byte[] pix,int width,int height,int featurecode){
        this.id=id;
        this.width=width;
        this.height=height;
        this.pixels=pix;
        this.featurecode=featurecode;
    }
    @TableId(type= IdType.AUTO)
    public int id;
    @Lob
    @JsonIgnore
    @Column(columnDefinition = "MEDIUMBLOB")
    public byte[] pixels;
    public int width;
    public int height;
    public int featurecode;
    @TableField(exist=false)
    public ArrayList<ArrayList<Integer>> pixel;
}
