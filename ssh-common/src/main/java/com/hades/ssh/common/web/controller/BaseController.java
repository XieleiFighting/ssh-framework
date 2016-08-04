package com.hades.ssh.common.web.controller;

import java.io.Serializable;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hades.ssh.common.entity.AbstractEntity;
import com.hades.ssh.common.utils.ReflectUtils;

/**
 * 基础控制器
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 上午9:36:58
 * <p>Version: 1.0
 */
public abstract class BaseController<T extends AbstractEntity<ID>, ID extends Serializable> {

	/**
	 * 实体类型
	 */
	protected final Class<T> entityClass;
	
	private String viewPrefix;
	
	protected BaseController() {
		this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
		setViewPrefix(defaultViewPrefix());
	}
	
	/**
     * 设置通用数据
     * @param model
     */
    protected void setCommonData(Model model) {
    }
    
    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     * @param viewPrefix
     */
    public void setViewPrefix(String viewPrefix) {
    	if (viewPrefix.startsWith("/")) {
    		viewPrefix = viewPrefix.substring(1);
    	}
    	this.viewPrefix = viewPrefix;
    }
    
    public String getViewPrefix() {
        return viewPrefix;
    }
    
    protected T newModel() {
    	try {
			return entityClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
		}
    }
    
    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     * @param suffixName
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }
    
    /**
     * 共享的验证规则
     * 验证失败返回true
     * @param t
     * @param result
     * @return
     */
    protected boolean hasError(T t, BindingResult result) {
        Assert.notNull(t);
        return result.hasErrors();
    }
    
    /**
     * @param backURL null 将重定向到默认getViewPrefix()
     * @return
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }
    
    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }

        return currentViewPrefix;
    }
}
