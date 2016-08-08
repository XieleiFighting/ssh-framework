package com.hades.ssh.service.maintain;

import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.maintain.NotificationTemplateDao;
import com.hades.ssh.entity.maintain.NotificationTemplate;

@Service
public class NotificationTemplateServiceImpl extends BaseServiceImpl<NotificationTemplate, Long> implements NotificationTemplateService {

	private NotificationTemplateDao notificationTemplateDao;
	
	@Override
	public NotificationTemplate findByName(String name) {
		return notificationTemplateDao.findByName(name);
	}

}
