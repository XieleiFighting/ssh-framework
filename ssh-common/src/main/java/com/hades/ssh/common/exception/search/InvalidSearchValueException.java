package com.hades.ssh.common.exception.search;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:12:45
 * <p>Version: 1.0
 */
public final class InvalidSearchValueException extends SearchException {

	private static final long serialVersionUID = 2825338675669144247L;

	public InvalidSearchValueException(String searchProperty, String entityProperty, Object value) {
        this(searchProperty, entityProperty, value, null);
    }

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value, Throwable cause) {
        super("Invalid Search Value, searchProperty [" + searchProperty + "], " +
                "entityProperty [" + entityProperty + "], value [" + value + "]", cause);
    }

}
