package com.hades.ssh.service.personal;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.personal.Calendar;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:14:15
 * <p>Version: 1.0
 */
public interface CalendarService extends BaseService<Calendar, Long> {

	void copyAndRemove(Calendar calendar);
	
	Long countRecentlyCalendar(Long userId, Integer interval);
}
