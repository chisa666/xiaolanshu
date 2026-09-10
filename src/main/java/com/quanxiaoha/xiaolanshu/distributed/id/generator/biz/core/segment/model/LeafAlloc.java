package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.model;

public class LeafAlloc {
    private String key;
    private long maxId;
    private int step;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public long getMaxId() { return maxId; }
    public void setMaxId(long maxId) { this.maxId = maxId; }
    public int getStep() { return step; }
    public void setStep(int step) { this.step = step; }
}
