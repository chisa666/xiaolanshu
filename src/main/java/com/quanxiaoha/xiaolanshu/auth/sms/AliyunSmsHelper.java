package com.quanxiaoha.xiaolanshu.auth.sms;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.quanxiaoha.framework.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 短信发送工具类
 **/
@Component
@Slf4j
public class AliyunSmsHelper {

    private final AliyunSmsProperties properties;
    private final ObjectProvider<com.aliyun.dysmsapi20170525.Client> dysmsapiClientProvider;
    private final ObjectProvider<com.aliyun.dypnsapi20170525.Client> dypnsapiClientProvider;

    public AliyunSmsHelper(AliyunSmsProperties properties,
                           ObjectProvider<com.aliyun.dysmsapi20170525.Client> dysmsapiClientProvider,
                           ObjectProvider<com.aliyun.dypnsapi20170525.Client> dypnsapiClientProvider) {
        this.properties = properties;
        this.dysmsapiClientProvider = dysmsapiClientProvider;
        this.dypnsapiClientProvider = dypnsapiClientProvider;
    }

    public boolean sendMessage(String phone, String verificationCode) {
        String provider = properties.getProvider();
        if ("dypnsapi".equalsIgnoreCase(provider)) {
            return sendByDypnsapi(phone, verificationCode);
        }
        return sendByDysmsapi(phone, verificationCode);
    }

    private boolean sendByDysmsapi(String phone, String verificationCode) {
        com.aliyun.dysmsapi20170525.Client client = dysmsapiClientProvider.getIfAvailable();
        if (client == null) {
            log.warn("未配置阿里云短信服务客户端，跳过发送，phone: {}", phone);
            return false;
        }
        String templateParam = String.format("{\"code\":\"%s\"}", verificationCode);
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(properties.getSignName())
                .setTemplateCode(properties.getTemplateCode())
                .setPhoneNumbers(phone)
                .setTemplateParam(templateParam);
        try {
            log.info("==> 开始短信发送, provider: dysmsapi, phone: {}, signName: {}, templateCode: {}",
                    phone, properties.getSignName(), properties.getTemplateCode());
            SendSmsResponse response = client.sendSmsWithOptions(request, runtimeOptions());
            log.info("==> 短信发送完成, provider: dysmsapi, response: {}", JsonUtils.toJsonString(response));
            return response.getBody() != null && "OK".equalsIgnoreCase(response.getBody().getCode());
        } catch (Exception error) {
            log.error("==> 短信发送错误, provider: dysmsapi", error);
            return false;
        }
    }

    private boolean sendByDypnsapi(String phone, String verificationCode) {
        com.aliyun.dypnsapi20170525.Client client = dypnsapiClientProvider.getIfAvailable();
        if (client == null) {
            log.warn("未配置阿里云号码认证客户端，跳过发送，phone: {}", phone);
            return false;
        }
        String templateParam = String.format("{\"code\":\"%s\",\"min\":\"%d\"}",
                verificationCode, properties.getValidityMinutes());
        SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest()
                .setSignName(properties.getSignName())
                .setTemplateCode(properties.getTemplateCode())
                .setPhoneNumber(phone)
                .setTemplateParam(templateParam);
        try {
            log.info("==> 开始短信发送, provider: dypnsapi, phone: {}, signName: {}, templateCode: {}",
                    phone, properties.getSignName(), properties.getTemplateCode());
            SendSmsVerifyCodeResponse response = client.sendSmsVerifyCodeWithOptions(request, runtimeOptions());
            log.info("==> 短信发送完成, provider: dypnsapi, response: {}", JsonUtils.toJsonString(response));
            return response.getBody() != null
                    && response.getBody().getSuccess() != null
                    && response.getBody().getSuccess();
        } catch (Exception error) {
            log.error("==> 短信发送错误, provider: dypnsapi", error);
            return false;
        }
    }

    private RuntimeOptions runtimeOptions() {
        return new RuntimeOptions()
                .setConnectTimeout(properties.getConnectTimeoutMillis())
                .setReadTimeout(properties.getReadTimeoutMillis())
                .setAutoretry(false)
                .setMaxAttempts(1);
    }
}
