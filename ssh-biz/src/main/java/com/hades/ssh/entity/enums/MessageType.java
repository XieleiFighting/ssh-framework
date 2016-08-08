package com.hades.ssh.entity.enums;

/**
 * 消息类型
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:01:34
 * <p>Version: 1.0
 */
public enum MessageType {
    user_message("普通消息"),
    system_message("系统消息");

    private final String info;

    private MessageType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    

}
