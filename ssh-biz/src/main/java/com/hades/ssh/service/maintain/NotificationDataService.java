package com.hades.ssh.service.maintain;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.maintain.NotificationData;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午6:00:55
 * <p>Version: 1.0
 */
public interface NotificationDataService extends BaseService<NotificationData, Long> {

	void markReadAll(final Long userId);
	
	void markRead(final Long notificationId);
}
