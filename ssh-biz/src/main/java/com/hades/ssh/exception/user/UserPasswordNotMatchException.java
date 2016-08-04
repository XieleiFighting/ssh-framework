package com.hades.ssh.exception.user;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:50:22
 * <p>Version: 1.0
 */
public class UserPasswordNotMatchException extends UserException {

	private static final long serialVersionUID = -117390168603642618L;

	public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
