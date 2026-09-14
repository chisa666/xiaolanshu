package com.quanxiaoha.xiaolanshu.user.biz.rpc;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Client for the distributed ID generator service.
 */
@Component
public class DistributedIdGeneratorRpcService {

    private static final String BIZ_TAG_XIAOLANSHU_ID = "leaf-segment-xiaolanshu-id";
    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    public String getXiaolanshuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOLANSHU_ID);
    }

    /** @deprecated use getXiaolanshuId */
    @Deprecated
    public String getxiaolanshuId() {
        return getXiaolanshuId();
    }

    /** @deprecated use getXiaolanshuId */
    @Deprecated
    public String getXiaohashuId() {
        return getXiaolanshuId();
    }

    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_USER_ID);
    }
}