package com.hades.ssh.extra.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.hades.ssh.common.cache.BaseCacheAspect;
import com.hades.ssh.entity.sys.User;

/**
 * 缓存及清理菜单缓存
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午4:24:58
 * <p>Version: 1.0
 */
@Component
@Aspect
public class ResourceMenuCacheAspect extends BaseCacheAspect {

	public ResourceMenuCacheAspect() {
        setCacheName("sys-menuCache");
    }
	
	private String menusKeyPrefix = "menus-";
	
	@Pointcut(value = "target(com.hades.ssh.service.sys.ResourceService)")
	private void resourceServicePointcut() {
    }
	
	@Pointcut(value = "execution(* save(..)) || execution(* update(..)) || execution(* delete(..))")
    private void resourceCacheEvictAllPointcut() {
    }
	
	@Pointcut(value = "execution(* findMenus(*)) && args(arg)", argNames = "arg")
    private void resourceCacheablePointcut(User arg) {
    }
	
	@Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
    public void findRolesCacheableAdvice() throws Throwable {
        clear();
    }
	
	@Around(value = "resourceServicePointcut() && resourceCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {

        User user = arg;

        String key = menusKey(user.getId());
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }
	
	public void evict(Long userId) {
        evict(menusKey(userId));
    }

    private String menusKey(Long userId) {
        return this.menusKeyPrefix + userId;
    }
}
