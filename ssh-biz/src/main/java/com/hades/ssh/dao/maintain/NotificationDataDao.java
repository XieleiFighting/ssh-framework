package com.hades.ssh.dao.maintain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.maintain.NotificationData;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:59:10
 * <p>Version: 1.0
 */
public interface NotificationDataDao extends BaseDao<NotificationData, Long> {

	@Modifying
    @Query("update NotificationData o set o.read=true where userId=?1")
    void markReadAll(Long userId);
}
