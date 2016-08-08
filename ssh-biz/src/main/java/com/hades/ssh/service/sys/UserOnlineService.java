package com.hades.ssh.service.sys;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.UserOnline;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 上午10:31:19
 * <p>Version: 1.0
 */
public interface UserOnlineService extends BaseService<UserOnline, String> {

	/**
	 * 上线
	 * @param userOnline
	 */
	void online(UserOnline userOnline);
	
	/**
	 * 下线
	 * @param sid
	 */
	void offline(String sid);
	
	/**
	 * 批量下线
	 * @param needOfflineIdList
	 */
	void batchOffline(List<String> needOfflineIdList);
	
	/**
	 * 无效的UserOnline
	 * @param expiredDate
	 * @param pageable
	 * @return
	 */
	Page<UserOnline> findExpiredUserOnlineList(Date expiredDate, Pageable pageable);
}
