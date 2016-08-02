package com.hades.ssh.common.utils;

import org.springframework.context.MessageSource;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月2日 下午5:57:49
 * <p>Version: 1.0
 */
public class MessageUtils {

	private static MessageSource messageSource;
	
	/**
     * 根据消息键和参数 获取消息
     * 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        if (messageSource == null) {
            messageSource = SpringUtils.getBean(MessageSource.class);
        }
        return messageSource.getMessage(code, args, null);
    }
}
