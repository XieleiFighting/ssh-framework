/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.exception.maintain;

import com.hades.ssh.common.exception.BaseException;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:54:39
 * <p>Version: 1.0
 */
public class TemplateException extends BaseException {

	private static final long serialVersionUID = -5274155181563692742L;

	public TemplateException(final String code, final Object[] args) {
        super("notification", code, args);
    }
}
