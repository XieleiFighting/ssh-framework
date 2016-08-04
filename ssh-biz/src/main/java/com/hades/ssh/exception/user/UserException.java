/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.exception.user;

import com.hades.ssh.common.exception.BaseException;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:49:21
 * <p>Version: 1.0
 */
public class UserException extends BaseException {

	private static final long serialVersionUID = 4336486832776472184L;

	public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
