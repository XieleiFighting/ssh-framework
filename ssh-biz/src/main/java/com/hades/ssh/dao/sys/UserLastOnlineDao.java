package com.hades.ssh.dao.sys;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.UserLastOnline;

public interface UserLastOnlineDao extends BaseDao<UserLastOnline, Long> {

	UserLastOnline findByUserId(Long userId);
}
