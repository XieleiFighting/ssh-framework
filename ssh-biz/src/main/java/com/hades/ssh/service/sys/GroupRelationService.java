package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.GroupRelation;

public interface GroupRelationService extends BaseService<GroupRelation, Long> {

	void appendRelation(Long groupId, Long[] organizationIds);
	
	void appendRelation(Long groupId, Long[] userIds, Long[] startUserIds, Long[] endUserIds);
	
	Set<Long> findGroupIds(Long userId, Set<Long> organizationIds);
	
}
