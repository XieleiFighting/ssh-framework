package com.hades.ssh.dao.sys;

import java.util.Set;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.Auth;

public interface AuthDao extends BaseDao<Auth, Long> {

	Auth findByUserId(Long userId);

    Auth findByGroupId(Long groupId);

    Auth findByOrganizationIdAndJobId(Long organizationId, Long jobId);

    ///////////委托给AuthRepositoryImpl实现
    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds, Set<Long[]> organizationJobIds);
}
