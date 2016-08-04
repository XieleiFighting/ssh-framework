package com.hades.ssh.common.exception.search;

import org.springframework.core.NestedRuntimeException;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:03:31
 * <p>Version: 1.0
 */
public class SearchException extends NestedRuntimeException {

	private static final long serialVersionUID = 2835425086433569515L;

	public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
