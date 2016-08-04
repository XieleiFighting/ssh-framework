package com.hades.ssh.entity.enums;

/**
 * 授权类型
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午5:35:50
 * <p>Version: 1.0
 */
public enum AuthType {

    user("用户"), user_group("用户组"), organization_job("组织机构和工作职务"), organization_group("组织机构组");

    private final String info;

    private AuthType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
