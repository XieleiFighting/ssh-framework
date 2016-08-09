/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.web.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hades.ssh.common.utils.LogUtils;
import com.hades.ssh.web.handler.entity.ExceptionResponse;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 下午2:36:25
 * <p>Version: 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        LogUtils.logError("用户权限验证失败", e);
        ExceptionResponse exceptionResponse = ExceptionResponse.from(e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("error", exceptionResponse);
        mv.setViewName("error/exception");

        return mv;
    }


}
