package com.quanxiaoha.xiaolanshu.kv.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: chisa
 * @description: RocketMQ 配置
 **/
@Configuration
@Import(RocketMQAutoConfiguration .class)
public class RocketMQConfig {
}

