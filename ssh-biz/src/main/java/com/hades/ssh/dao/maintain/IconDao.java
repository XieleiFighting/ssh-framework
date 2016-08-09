package com.hades.ssh.dao.maintain;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.maintain.Icon;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:18:04
 * <p>Version: 1.0
 */
public interface IconDao extends BaseDao<Icon, Long> {

	Icon findByIdentity(String identity);
}
