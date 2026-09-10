package com.quanxiaoha.xiaolanshu.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.user.biz.domain.mapper")
public class xiaolanshuUserBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuUserBizApplication.class, args);
    }

}

