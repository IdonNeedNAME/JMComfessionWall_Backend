package com.github.idonneedname.jmcomfessionwall_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.idonneedname.jmcomfessionwall_backend.mapper")
public class JmComfessionWallBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmComfessionWallBackendApplication.class, args);
    }

}
