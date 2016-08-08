package com.hades.ssh.service.personal;

import java.sql.Time;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.personal.CalendarDao;
import com.hades.ssh.entity.personal.Calendar;

@Service
public class CalendarServiceImpl extends BaseServiceImpl<Calendar, Long> implements CalendarService {

	@Autowired
	private CalendarDao calendarDao;
	
	@Override
	public void copyAndRemove(Calendar calendar) {
		delete(calendar);

        Calendar copyCalendar = new Calendar();
        BeanUtils.copyProperties(calendar, copyCalendar);
        copyCalendar.setId(null);
        save(copyCalendar);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long countRecentlyCalendar(Long userId, Integer interval) {
		Date nowDate = new Date();
        Date nowTime = new Time(nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
        nowDate.setHours(0);
        nowDate.setMinutes(0);
        nowDate.setSeconds(0);
		return calendarDao.countRecentlyCalendar(userId, nowDate, nowTime, interval);
	}

}
