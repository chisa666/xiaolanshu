package com.quanxiaoha.xiaolanshu.comment.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.comment.biz.domain.mapper")
public class xiaolanshuCommentBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuCommentBizApplication.class, args);
    }

}

