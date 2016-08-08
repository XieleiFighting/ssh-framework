package com.hades.ssh.service.sys;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.UserLastOnline;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 上午10:29:03
 * <p>Version: 1.0
 */
public interface UserLastOnlineService extends BaseService<UserLastOnline, Long> {

	UserLastOnline findByUserId(Long userId);
	
	void lastOnline(UserLastOnline lastOnline);
}
