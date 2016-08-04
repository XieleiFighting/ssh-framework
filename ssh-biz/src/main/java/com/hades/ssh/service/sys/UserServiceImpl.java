package com.hades.ssh.service.sys;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.UserDao;
import com.hades.ssh.entity.sys.User;

public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
    private PasswordService passwordService;
	
	@Autowired
//    private UserStatusHistoryService userStatusHistoryService;
	
	@Override
    public User save(User user) {
        if (user.getCreateDate() == null) {
            user.setCreateDate(new Date());
        }
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));

        return super.save(user);
    }
}
