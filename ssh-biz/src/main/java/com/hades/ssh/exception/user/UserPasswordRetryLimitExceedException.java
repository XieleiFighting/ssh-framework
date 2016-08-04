package com.hades.ssh.exception.user;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:50:30
 * <p>Version: 1.0
 */
public class UserPasswordRetryLimitExceedException extends UserException {
	
	private static final long serialVersionUID = -8586056650387995274L;

	public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[]{retryLimitCount});
    }
}
