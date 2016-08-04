package com.hades.ssh.exception.user;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:49:39
 * <p>Version: 1.0
 */
public class UserBlockedException extends UserException {

	private static final long serialVersionUID = -7275815225449821527L;

	public UserBlockedException(String reason) {
        super("user.blocked", new Object[]{reason});
    }
}
