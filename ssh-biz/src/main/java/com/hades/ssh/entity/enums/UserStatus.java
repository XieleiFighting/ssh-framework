package com.hades.ssh.entity.enums;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午5:14:23
 * <p>Version: 1.0
 */
public enum UserStatus {

    normal("正常状态"), blocked("封禁状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
