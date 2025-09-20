package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@TableName(value = "report")
@Data
@Builder
public class Report {
    public Report(int id,int targetype,String content,int status,int result)
    {
        this.id=id;
        this.targetype=targetype;
        this.content=content;
        this.status=status;
        this.result=result;
    }
    @TableId(type= IdType.AUTO)
    public int id;
    public int targetype;//分辨帖子还是评论
    public String content;
    public int status;
    public int result;


}
