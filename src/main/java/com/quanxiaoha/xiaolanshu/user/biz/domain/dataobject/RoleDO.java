package com.quanxiaoha.xiaolanshu.user.biz.domain.dataobject;

import lombok.Data;

@Data
public class RoleDO {
    private Long id;
    private String roleName;
    private String roleKey;
    private Integer status;
    private Integer sort;
    private String remark;
}
