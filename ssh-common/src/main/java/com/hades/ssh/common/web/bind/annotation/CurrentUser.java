package com.hades.ssh.common.web.bind.annotation;

import java.lang.annotation.*;

import com.hades.ssh.common.Constants;

/**
 * 绑定当前登录的用户
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午3:02:31
 * <p>Version: 1.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

	/**
     * 当前用户在request中的名字 默认{@link Constants#CURRENT_USER}
     *
     * @return
     */
    String value() default Constants.CURRENT_USER;
}
