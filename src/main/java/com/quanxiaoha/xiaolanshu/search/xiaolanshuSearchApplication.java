package com.quanxiaoha.xiaolanshu.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.quanxiaoha.xiaolanshu.search.biz")
@EnableScheduling
@MapperScan("com.quanxiaoha.xiaolanshu.search.biz.domain.mapper")
public class xiaolanshuSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuSearchApplication.class, args);
    }

}

