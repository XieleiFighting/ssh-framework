package com.hades.ssh.service.sys;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.enums.UserStatus;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.entity.sys.UserStatusHistory;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 上午10:36:04
 * <p>Version: 1.0
 */
public interface UserStatusHistoryService extends BaseService<UserStatusHistory, Long> {

	void log(User opUser, User user, UserStatus newStatus, String reason);
	
	UserStatusHistory findLastHistory(final User user);
	
	String getLastReason(User user);
}
