package com.hades.ssh.dao.sys;

import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.Role;
import com.hades.ssh.entity.sys.RoleResourcePermission;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午6:42:05
 * <p>Version: 1.0
 */
public interface RoleDao extends BaseDao<Role, Long> {

	@Query("from RoleResourcePermission where role=?1 and resourceId=?2")
    RoleResourcePermission findRoleResourcePermission(Role role, Long resourceId);
}
