package com.hades.ssh.common.dao.support.annotation;

import java.lang.annotation.*;

/**
 * 开启查询缓存
 *
 * @author Zhang Kaitao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    boolean value() default true;

}
