package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common;

public final class Result {
    private final long id;
    private final Status status;

    public Result(long id, Status status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Result{id=" + id + ", status=" + status + '}';
    }
}
