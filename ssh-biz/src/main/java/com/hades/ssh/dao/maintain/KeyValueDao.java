package com.hades.ssh.dao.maintain;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.maintain.KeyValue;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:24:28
 * <p>Version: 1.0
 */
public interface KeyValueDao extends BaseDao<KeyValue, Long> {

	KeyValue findByKey(String key);
}
