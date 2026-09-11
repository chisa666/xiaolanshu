package com.quanxiaoha.framework.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {

    private static volatile ObjectMapper OBJECT_MAPPER = createDefaultObjectMapper();

    private static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModules(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * 使用 Spring Boot 个性化配置的 ObjectMapper 初始化 JSON 工具类。
     */
    public static void init(ObjectMapper objectMapper) {
        if (objectMapper != null) {
            OBJECT_MAPPER = objectMapper;
        }
    }

    /**
     * 将对象转换为 JSON 字符串。
     */
    @SneakyThrows
    public static String toJsonString(Object obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.isBlank()) {
            return null;
        }
        return OBJECT_MAPPER.readValue(jsonStr, clazz);
    }

    @SneakyThrows
    public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.isBlank()) {
            return List.of();
        }
        var type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return OBJECT_MAPPER.readValue(jsonStr, type);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        if (jsonStr == null || jsonStr.isBlank()) {
            return Map.of();
        }
        var type = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return OBJECT_MAPPER.readValue(jsonStr, type);
    }

    @SneakyThrows
    public static <T> Set<T> parseSet(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.isBlank()) return Set.of();
        var type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(Set.class, clazz);
        return OBJECT_MAPPER.readValue(jsonStr, type);
    }

}
