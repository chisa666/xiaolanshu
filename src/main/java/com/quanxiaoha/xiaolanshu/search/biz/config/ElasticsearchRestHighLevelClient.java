package com.quanxiaoha.xiaolanshu.search.biz.config;

import jakarta.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: ElasticsearchRestClient 客户端
 **/
@Configuration
public class ElasticsearchRestHighLevelClient {

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    private static final String COLON = ":";
    private static final String HTTP = "http";

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String address = elasticsearchProperties.getAddress();
        if (address == null || address.isBlank()) {
            address = "127.0.0.1:9200";
        }
        URI uri = URI.create(address.contains("://") ? address : HTTP + "://" + address);
        String host = uri.getHost();
        int port = uri.getPort() > 0 ? uri.getPort() : 9200;
        if (host == null || host.isBlank()) {
            throw new IllegalStateException("elasticsearch.address 未配置为 host:port");
        }

        HttpHost httpHost = new HttpHost(host, port, HTTP);

        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
