package com.hades.ssh.service.maintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.maintain.NotificationDataDao;
import com.hades.ssh.entity.maintain.NotificationData;

@Service
public class NotificationDataServiceImpl extends BaseServiceImpl<NotificationData, Long> implements NotificationDataService {

	@Autowired
	private NotificationDataDao notificationDataDao;
	
	@Override
	public void markReadAll(Long userId) {
		notificationDataDao.markReadAll(userId);
	}

	@Override
	public void markRead(Long notificationId) {
		NotificationData data = findOne(notificationId);
        if(data == null || data.getRead().equals(Boolean.TRUE)) {
            return;
        }
        data.setRead(Boolean.TRUE);
        update(data);
	}

}
