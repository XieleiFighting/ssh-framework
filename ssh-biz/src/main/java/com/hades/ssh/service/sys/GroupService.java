package com.hades.ssh.service.sys;

import java.util.Map;
import java.util.Set;

import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.sys.Group;

public interface GroupService extends BaseService<Group, Long> {

	Set<Map<String, Object>> findIdAndNames(Searchable searchable, String groupName);
	
	/**
	 * 获取可用的的分组编号列表
	 * @param userId
	 * @param organizationIds
	 * @return
	 */
	Set<Long> findShowGroupIds(Long userId, Set<Long> organizationIds);
	
}
