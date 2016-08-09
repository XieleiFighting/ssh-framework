package com.hades.ssh.service.maintain;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.maintain.Icon;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:16:55
 * <p>Version: 1.0
 */
public interface IconService extends BaseService<Icon, Long> {

	Icon findByIdentity(String identity);
}
