package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "report")
@Data
@NoArgsConstructor
public class Report {
    public Report(int id,int targetype,String content,int status,int result,int targetid,String reason,int host){
        this.id = id;
        this.targetype = targetype;
        this.content = content;
        this.status = status;
        this.result = result;
        this.targetid = targetid;
        this.reason = reason;
        this.host = host;
    }
    @TableId(type= IdType.AUTO)
    public int id;
    public int targetype;//分辨帖子还是评论
    public String content;
    public int status;
    public int result;
    public int targetid;
    public int host;
    public String reason;
}
