package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Optional Leaf settings. External infrastructure is opt-in so local development
 * can still use the built-in generators.
 */
@Component
@ConfigurationProperties(prefix = "leaf")
@Data
public class LeafProperties {
    private boolean segmentEnabled;
    private boolean snowflakeEnabled = true;
    private long snowflakeWorkerId;
}