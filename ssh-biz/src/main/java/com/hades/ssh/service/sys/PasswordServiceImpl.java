package com.hades.ssh.service.sys;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.utils.security.Md5Utils;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.exception.user.UserPasswordNotMatchException;
import com.hades.ssh.exception.user.UserPasswordRetryLimitExceedException;
import com.hades.ssh.utils.UserLogUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class PasswordServiceImpl implements PasswordService {
	
	@Autowired
    private CacheManager ehcacheManager;
	
	private Cache loginRecordCache;
	
	@Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount = 10;
	
	public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @PostConstruct
    public void init() {
        loginRecordCache = ehcacheManager.getCache("loginRecordCache");
    }

	@Override
	public void validate(User user, String password) {
		String username = user.getUsername();

        int retryCount = 0;

        Element cacheElement = loginRecordCache.get(username);
        if (cacheElement != null) {
            retryCount = (Integer) cacheElement.getObjectValue();
            if (retryCount >= maxRetryCount) {
                UserLogUtils.log(
                        username,
                        "passwordError",
                        "password error, retry limit exceed! password: {},max retry count {}",
                        password, maxRetryCount);
                throw new UserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }

        if (!matches(user, password)) {
            loginRecordCache.put(new Element(username, ++retryCount));
            UserLogUtils.log(
                    username,
                    "passwordError",
                    "password error! password: {} retry count: {}",
                    password, retryCount);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
	}

	@Override
	public boolean matches(User user, String newPassword) {
		return user.getPassword().equals(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
	}

	@Override
	public void clearLoginRecordCache(String username) {
		loginRecordCache.remove(username);
	}

	@Override
	public String encryptPassword(String username, String password, String salt) {
		return Md5Utils.hash(username + password + salt);
	}

}
