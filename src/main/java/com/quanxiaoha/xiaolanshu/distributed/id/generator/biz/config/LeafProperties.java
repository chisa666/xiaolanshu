package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Leaf settings. External infrastructure is opt-in so local development can still use the generators.
 */
@Component
@ConfigurationProperties(prefix = "leaf")
@Data
public class LeafProperties {
    private boolean segmentEnabled;
    private boolean snowflakeEnabled = true;
    private boolean snowflakeZookeeperEnabled;
    private String snowflakeZkAddress = "127.0.0.1:2181";
    private int snowflakePort = 2222;
    private long snowflakeWorkerId;
}