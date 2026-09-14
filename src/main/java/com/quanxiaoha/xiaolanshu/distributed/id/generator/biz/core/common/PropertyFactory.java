package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertyFactory {
    private static final Logger logger = LoggerFactory.getLogger(PropertyFactory.class);
    private static final Properties prop = new Properties();
    static {
        try {
            var resource = PropertyFactory.class.getClassLoader().getResourceAsStream("leaf.properties");
            if (resource != null) {
                prop.load(resource);
            }
        } catch (IOException e) {
            logger.warn("Load Properties Ex", e);
        }
    }
    public static Properties getProperties() {
        return prop;
    }
}
