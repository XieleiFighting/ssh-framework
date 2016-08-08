package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.entity.sys.Role;
import com.hades.ssh.entity.sys.User;

public interface UserAuthService {

	Set<Role> findRoles(User user);
	
	Set<String> findStringRoles(User user);
	
	Set<String> findStringPermissions(User user);
}
