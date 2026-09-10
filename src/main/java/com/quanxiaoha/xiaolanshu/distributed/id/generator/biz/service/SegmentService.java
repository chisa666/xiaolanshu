package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;

public interface SegmentService {
    Result getId(String key);
}
