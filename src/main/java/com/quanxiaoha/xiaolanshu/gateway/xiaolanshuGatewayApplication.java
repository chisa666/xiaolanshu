package com.quanxiaoha.xiaolanshu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = cn.dev33.satoken.spring.SaTokenContextRegister.class)
public class xiaolanshuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(xiaolanshuGatewayApplication.class);
        application.setAdditionalProfiles("gateway");
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

}
