package com.github.idonneedname.jmcomfessionwall_backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "apikey")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey {
    public int id;
    public String apikey;
    public long lastoperatetime;
}
