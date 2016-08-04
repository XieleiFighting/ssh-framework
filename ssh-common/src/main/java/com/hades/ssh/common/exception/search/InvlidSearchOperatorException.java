package com.hades.ssh.common.exception.search;

import com.hades.ssh.common.entity.search.SearchOperator;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:11:31
 * <p>Version: 1.0
 */
public final class InvlidSearchOperatorException extends SearchException {

	private static final long serialVersionUID = -2802271595094341119L;

	public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
