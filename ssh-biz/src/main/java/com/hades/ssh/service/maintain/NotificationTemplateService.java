package com.hades.ssh.service.maintain;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.maintain.NotificationTemplate;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午6:02:15
 * <p>Version: 1.0
 */
public interface NotificationTemplateService extends BaseService<NotificationTemplate, Long> {

	NotificationTemplate findByName(final String name);
}
