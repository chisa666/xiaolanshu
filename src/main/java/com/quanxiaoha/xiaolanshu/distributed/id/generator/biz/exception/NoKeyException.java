package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.exception;

public class NoKeyException extends RuntimeException {
    public NoKeyException() {
        super("key 不能为空");
    }
}
