package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@TableName(value = "adminwhitelist")
@Data
@Builder
@AllArgsConstructor
public class AdminWhiteList {
    @TableId(type= IdType.AUTO)
    public int id;
    public String username;
}
