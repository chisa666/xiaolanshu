package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import org.springframework.stereotype.Service;

@Service
public class SnowflakeServiceImpl implements SnowflakeService {
    @Override
    public Result getId(String key) {
        if (key == null || key.isBlank()) return new Result(0, Status.EXCEPTION);
        long timestamp = System.currentTimeMillis() << 12;
        return new Result(timestamp | (Thread.currentThread().getId() & 4095), Status.SUCCESS);
    }
}
