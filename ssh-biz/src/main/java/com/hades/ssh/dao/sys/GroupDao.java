package com.hades.ssh.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.Group;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午6:00:22
 * <p>Version: 1.0
 */
public interface GroupDao extends BaseDao<Group, Long> {

    @Query("select id from Group where defaultGroup=true and show=true")
    List<Long> findDefaultGroupIds();

}
