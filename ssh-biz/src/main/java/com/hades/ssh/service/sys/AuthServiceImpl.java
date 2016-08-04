package com.hades.ssh.service.sys;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.AuthDao;
import com.hades.ssh.entity.sys.Auth;
import com.hades.ssh.entity.sys.Group;
import com.hades.ssh.entity.sys.User;

public class AuthServiceImpl extends BaseServiceImpl<Auth, Long> implements AuthService {

	@Autowired
	private AuthDao authDao;
	
	@Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

	@Override
	public void addUserAuth(Long[] userIds, Auth m) {
		if (ArrayUtils.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {

            User user = userService.findOne(userId);
            if (user == null) {
                continue;
            }

            Auth auth = authDao.findByUserId(userId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setUserId(userId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
	}

	@Override
	public void addGroupAuth(Long[] groupIds, Auth m) {
		if (ArrayUtils.isEmpty(groupIds)) {
            return;
        }

        for (Long groupId : groupIds) {
            Group group = groupService.findOne(groupId);
            if (group == null) {
                continue;
            }

            Auth auth = authDao.findByGroupId(groupId);
            if (auth != null) {
                auth.addRoleIds(m.getRoleIds());
                continue;
            }
            auth = new Auth();
            auth.setGroupId(groupId);
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            save(auth);
        }
	}

	@Override
	public void addOrganizationJobAuth(Long[] organizationIds, Long[][] jobIds, Auth m) {
		if (ArrayUtils.isEmpty(organizationIds)) {
            return;
        }
        for (int i = 0, l = organizationIds.length; i < l; i++) {
            Long organizationId = organizationIds[i];
            if (jobIds[i].length == 0) {
                addOrganizationJobAuth(organizationId, null, m);
                continue;
            }

            //仅新增/修改一个 spring会自动split（“，”）--->给数组
            if (l == 1) {
                for (int j = 0, l2 = jobIds.length; j < l2; j++) {
                    Long jobId = jobIds[i][0];
                    addOrganizationJobAuth(organizationId, jobId, m);
                }
            } else {
                for (int j = 0, l2 = jobIds[i].length; j < l2; j++) {
                    Long jobId = jobIds[i][0];
                    addOrganizationJobAuth(organizationId, jobId, m);
                }
            }

        }
	}
	
	private void addOrganizationJobAuth(Long organizationId, Long jobId, Auth m) {
        if (organizationId == null) {
            organizationId = 0L;
        }
        if (jobId == null) {
            jobId = 0L;
        }

        Auth auth = authDao.findByOrganizationIdAndJobId(organizationId, jobId);
        if (auth != null) {
            auth.addRoleIds(m.getRoleIds());
            return;
        }

        auth = new Auth();
        auth.setOrganizationId(organizationId);
        auth.setJobId(jobId);
        auth.setType(m.getType());
        auth.setRoleIds(m.getRoleIds());
        save(auth);

    }

	@Override
	public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds,
			Set<Long[]> organizationJobIds) {
		return authDao.findRoleIds(userId, groupIds, organizationIds, jobIds, organizationJobIds);
	}
    
    
}
