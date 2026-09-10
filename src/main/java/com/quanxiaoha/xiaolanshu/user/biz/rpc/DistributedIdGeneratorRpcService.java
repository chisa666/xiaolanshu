package com.quanxiaoha.xiaolanshu.user.biz.rpc;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 分布式 ID 生成服务
 **/
@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * Leaf 号段模式：小哈书 ID 业务标识
     */
    private static final String BIZ_TAG_xiaolanshu_ID = "leaf-segment-xiaolanshu-id";

    /**
     * 调用分布式 ID 生成服务生成小哈书 ID
     *
     * @return
     */
    public String getxiaolanshuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_xiaolanshu_ID);
    }

    public String getXiaolanshuId() {
        return getxiaolanshuId();
    }

    public String getXiaohashuId() {
        return getxiaolanshuId();
    }

    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId("leaf-segment-user-id");
    }
}

