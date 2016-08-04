package com.hades.ssh.common.exception.search;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:11:09
 * <p>Version: 1.0
 */
public final class InvalidSearchPropertyException extends SearchException {

	private static final long serialVersionUID = -3581652033832235602L;

	public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
        this(searchProperty, entityProperty, null);
    }

    public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
        super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
    }


}
