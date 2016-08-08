package com.hades.ssh.dao.maintain;

import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.maintain.NotificationTemplate;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:59:05
 * <p>Version: 1.0
 */
public interface NotificationTemplateDao extends BaseDao<NotificationTemplate, Long> {

	@Query("from NotificationTemplate o where name=?1")
    NotificationTemplate findByName(String name);
}
