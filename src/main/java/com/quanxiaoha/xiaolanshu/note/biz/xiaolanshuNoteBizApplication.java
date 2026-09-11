package com.quanxiaoha.xiaolanshu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.quanxiaoha.xiaolanshu")
@ComponentScan("com.quanxiaoha.xiaolanshu.count.fallback")
public class xiaolanshuNoteBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuNoteBizApplication.class, args);
    }

}

