package com.quanxiaoha.xiaolanshu.data.align;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quanxiaoha.xiaolanshu.data.align.domain.mapper")
public class xiaolanshuDataAlignApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuDataAlignApplication.class, args);
    }

}

