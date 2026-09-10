package com.quanxiaoha.xiaolanshu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.quanxiaoha.xiaolanshu")
public class xiaolanshuNoteBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuNoteBizApplication.class, args);
    }

}

