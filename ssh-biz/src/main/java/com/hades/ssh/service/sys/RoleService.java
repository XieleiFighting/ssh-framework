package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.Role;

public interface RoleService extends BaseService<Role, Long> {

	/**
	 * 获取可用的角色列表
	 * @param roleIds
	 * @return
	 */
	Set<Role> findShowRoles(Set<Long> roleIds);
}
