package com.hades.ssh.service.maintain;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.maintain.KeyValue;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:22:10
 * <p>Version: 1.0
 */
public interface KeyValueService extends BaseService<KeyValue, Long> {

	KeyValue findByKey(String key);
}
