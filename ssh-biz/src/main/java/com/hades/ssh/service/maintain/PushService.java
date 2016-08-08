package com.hades.ssh.service.maintain;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:40:44
 * <p>Version: 1.0
 */
public interface PushService {

	boolean isOnline(final Long userId);
	
	void online(final Long userId);
	
	void offline(final Long userId);
	
	@SuppressWarnings("rawtypes")
	DeferredResult newDeferredResult(final Long userId);
	
	void push(final Long userId, final Object data);
	
	/**
	 * 定期清空队列 防止中间推送消息时中断造成消息丢失
	 */
	@Scheduled(fixedRate = 5L * 60 * 1000)
	void sync();
}
