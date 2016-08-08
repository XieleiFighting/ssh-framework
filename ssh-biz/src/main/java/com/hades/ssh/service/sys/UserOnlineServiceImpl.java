package com.hades.ssh.service.sys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.UserOnlineDao;
import com.hades.ssh.entity.sys.UserOnline;

@Service
public class UserOnlineServiceImpl extends BaseServiceImpl<UserOnline, String> implements UserOnlineService {

	@Autowired
	private UserOnlineDao userOnlineDao;
	
	@Override
	public void online(UserOnline userOnline) {
		save(userOnline);
	}

	@Override
	public void offline(String sid) {
		UserOnline userOnline = findOne(sid);
        if (userOnline != null) {
            delete(userOnline);
        }
        //游客 无需记录上次访问记录
        //此处使用数据库的触发器完成同步
//        if(userOnline.getUserId() == null) {
//            userLastOnlineService.lastOnline(UserLastOnline.fromUserOnline(userOnline));
//        }
	}

	@Override
	public void batchOffline(List<String> needOfflineIdList) {
		userOnlineDao.batchDelete(needOfflineIdList);
	}

	@Override
	public Page<UserOnline> findExpiredUserOnlineList(Date expiredDate, Pageable pageable) {
		return userOnlineDao.findExpiredUserOnlineList(expiredDate, pageable);
	}

}
