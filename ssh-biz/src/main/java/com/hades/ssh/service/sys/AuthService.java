package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.Auth;

public interface AuthService extends BaseService<Auth, Long> {

	void addUserAuth(Long[] userIds, Auth m);
	
	void addGroupAuth(Long[] groupIds, Auth m);
	
	void addOrganizationJobAuth(Long[] organizationIds, Long[][] jobIds, Auth m);
	
	Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds, Set<Long[]> organizationJobIds);
}
