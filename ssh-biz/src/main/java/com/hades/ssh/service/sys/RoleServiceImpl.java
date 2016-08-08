package com.hades.ssh.service.sys;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.RoleDao;
import com.hades.ssh.entity.sys.Role;
import com.hades.ssh.entity.sys.RoleResourcePermission;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	@Override
    public Role update(Role role) {
        List<RoleResourcePermission> localResourcePermissions = role.getResourcePermissions();
        for (int i = 0, l = localResourcePermissions.size(); i < l; i++) {
            RoleResourcePermission localResourcePermission = localResourcePermissions.get(i);
            localResourcePermission.setRole(role);
            RoleResourcePermission dbResourcePermission = findRoleResourcePermission(localResourcePermission);
            if (dbResourcePermission != null) {//出现在先删除再添加的情况
                dbResourcePermission.setRole(localResourcePermission.getRole());
                dbResourcePermission.setResourceId(localResourcePermission.getResourceId());
                dbResourcePermission.setPermissionIds(localResourcePermission.getPermissionIds());
                localResourcePermissions.set(i, dbResourcePermission);
            }
        }
        return super.update(role);
    }
	
	private RoleResourcePermission findRoleResourcePermission(RoleResourcePermission roleResourcePermission) {
        return roleDao.findRoleResourcePermission(
                roleResourcePermission.getRole(), roleResourcePermission.getResourceId());
    }

	@Override
	public Set<Role> findShowRoles(Set<Long> roleIds) {
		Set<Role> roles = Sets.newHashSet();

        //TODO 如果角色很多 此处应该写查询
        for (Role role : findAll()) {
            if (Boolean.TRUE.equals(role.getShow()) && roleIds.contains(role.getId())) {
                roles.add(role);
            }
        }
        return roles;
	}

}
