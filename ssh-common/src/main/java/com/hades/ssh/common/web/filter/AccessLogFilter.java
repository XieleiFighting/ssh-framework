/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.common.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hades.ssh.common.utils.LogUtils;

/**
 * 记录访问日志
 */
public class AccessLogFilter extends BaseFilter {


    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LogUtils.logAccess(request);
        chain.doFilter(request, response);
    }


}
