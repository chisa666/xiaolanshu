package com.quanxiaoha.xiaolanshu.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: Elasticsearch 配置项
 **/
@ConfigurationProperties(prefix = "elasticsearch")
@Component
@Data
public class ElasticsearchProperties {
    private String address;
}

