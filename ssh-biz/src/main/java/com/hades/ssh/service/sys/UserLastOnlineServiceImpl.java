package com.hades.ssh.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.UserLastOnlineDao;
import com.hades.ssh.entity.sys.UserLastOnline;

@Service
public class UserLastOnlineServiceImpl extends BaseServiceImpl<UserLastOnline, Long> implements UserLastOnlineService {

	@Autowired
	private UserLastOnlineDao userLastOnlineDao;
	
	@Override
	public UserLastOnline findByUserId(Long userId) {
		return userLastOnlineDao.findByUserId(userId);
	}

	@Override
	public void lastOnline(UserLastOnline lastOnline) {
		UserLastOnline dbLastOnline = findByUserId(lastOnline.getUserId());

        if (dbLastOnline == null) {
            dbLastOnline = lastOnline;
        } else {
            UserLastOnline.merge(lastOnline, dbLastOnline);
        }
        dbLastOnline.incLoginCount();
        dbLastOnline.incTotalOnlineTime();
        //相对于save or update
        save(dbLastOnline);
	}

	
}
