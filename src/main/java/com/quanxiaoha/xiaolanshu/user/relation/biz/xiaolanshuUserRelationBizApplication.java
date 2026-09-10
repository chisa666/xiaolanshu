package com.quanxiaoha.xiaolanshu.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.user.relation.biz.domain.mapper")
public class xiaolanshuUserRelationBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuUserRelationBizApplication.class, args);
    }

}

