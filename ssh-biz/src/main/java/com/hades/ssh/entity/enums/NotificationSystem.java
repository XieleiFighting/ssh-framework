package com.hades.ssh.entity.enums;

/**
 * 触发的子系统
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:56:24
 * <p>Version: 1.0
 */
public enum NotificationSystem {

    system("系统"), excel("excel");

    private final String info;

    private NotificationSystem(final String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
