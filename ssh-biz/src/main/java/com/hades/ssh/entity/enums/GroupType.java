package com.hades.ssh.entity.enums;

/**
 * 用户组分类
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午5:47:04
 * <p>Version: 1.0
 */
public enum GroupType {

    user("用户组"), organization("组织机构组");

    private final String info;

    private GroupType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
