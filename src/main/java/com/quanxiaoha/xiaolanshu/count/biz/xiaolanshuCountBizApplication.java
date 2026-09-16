package com.quanxiaoha.xiaolanshu.count.biz;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {GatewayAutoConfiguration.class, GatewayClassPathWarningAutoConfiguration.class, SaTokenContextRegister.class},
        excludeName = {
        "org.springframework.cloud.gateway.config.GatewayRedisAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayResilience4JCircuitBreakerAutoConfiguration",
        "org.springframework.cloud.gateway.config.SimpleUrlHandlerMappingGlobalCorsAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveOAuth2AutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayMetricsAutoConfiguration",
        "org.springframework.cloud.gateway.config.LocalResponseCacheAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayNoLoadBalancerClientAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration"
})
@MapperScan("com.quanxiaoha.xiaolanshu.count.biz.domain.mapper")
public class xiaolanshuCountBizApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(xiaolanshuCountBizApplication.class);
        application.setAdditionalProfiles("count");
        application.run(args);

        // 初始化限流规则
        initFlowRules();
    }

    /**
     * 初始化限流规则
     */
    private static void initFlowRules() {
        // 1. 创建一个集合，用于存放所有限流规则
        List<FlowRule> rules = new ArrayList<>();

        // 2. 创建一条新的流量控制规则
        FlowRule rule = new FlowRule();

        // 3. 设置需要保护的资源名称（通常是被限流的方法名）
        rule.setResource("findUserCountData");

        // 4. 设置限流阈值类型为 QPS（每秒查询数）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        // 5. 设置允许的 QPS 阈值（这里设置为每秒最多5次请求）
        rule.setCount(5);

        // 6. 将当前规则添加到规则列表中
        rules.add(rule);

        // 7. 加载规则列表到 Sentinel 的规则管理器（使规则生效）
        FlowRuleManager.loadRules(rules);
    }
}

