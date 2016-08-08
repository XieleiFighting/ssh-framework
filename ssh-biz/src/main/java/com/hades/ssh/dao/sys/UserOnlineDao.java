package com.hades.ssh.dao.sys;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.UserOnline;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午2:38:40
 * <p>Version: 1.0
 */
public interface UserOnlineDao extends BaseDao<UserOnline, String> {

	@Query("from UserOnline o where o.lastAccessTime < ?1 order by o.lastAccessTime asc")
    Page<UserOnline> findExpiredUserOnlineList(Date expiredDate, Pageable pageable);

    @Modifying
    @Query("delete from UserOnline o where o.id in (?1)")
    void batchDelete(List<String> needExpiredIdList);
    
}
