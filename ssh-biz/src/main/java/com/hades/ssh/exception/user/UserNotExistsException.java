package com.hades.ssh.exception.user;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:50:04
 * <p>Version: 1.0
 */
public class UserNotExistsException extends UserException {

	private static final long serialVersionUID = -6412713135961362343L;

	public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
