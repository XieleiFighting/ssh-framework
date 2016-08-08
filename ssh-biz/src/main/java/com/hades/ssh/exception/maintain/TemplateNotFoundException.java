/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.exception.maintain;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:54:50
 * <p>Version: 1.0
 */
public class TemplateNotFoundException extends TemplateException {
	
	private static final long serialVersionUID = -7546730530456689370L;

	public TemplateNotFoundException(String templateName) {
        super("notification.template.not.found", new Object[] {templateName});
    }
}
