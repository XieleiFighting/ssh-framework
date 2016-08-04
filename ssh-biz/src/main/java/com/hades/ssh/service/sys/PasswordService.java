package com.hades.ssh.service.sys;

import com.hades.ssh.entity.sys.User;

public interface PasswordService {

	void validate(User user, String password);
	
	boolean matches(User user, String newPassword);
	
	void clearLoginRecordCache(String username);
	
	String encryptPassword(String username, String password, String salt);
}
