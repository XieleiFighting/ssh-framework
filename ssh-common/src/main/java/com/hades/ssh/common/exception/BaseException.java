package com.hades.ssh.common.exception;

import org.apache.commons.lang3.StringUtils;

import com.hades.ssh.common.utils.MessageUtils;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 913183719840198585L;
	
	/** 所属模块 */
	private String module;
	
	/** 错误码 */
	private String code;
	
	/** 错误码对应的参数 */
	private Object[] args;
	
	/** 默认的错误消息 */
	private String defaultMessage;

	public BaseException(String module, String code, Object[] args, String defaultMessage) {
		super();
		this.module = module;
		this.code = code;
		this.args = args;
		this.defaultMessage = defaultMessage;
	}

	public BaseException(String module, String code, Object[] args) {
		this(module, code, args, null);
	}

	public BaseException(String code, Object[] args) {
		this(null, code, args, null);
	}

	public BaseException(String module, String defaultMessage) {
		this(module, null, null, defaultMessage);
	}
	
	public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

	public String getModule() {
		return module;
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
	
	@Override
    public String getMessage() {
		String message = null;
		if (!StringUtils.isEmpty(code)) {
			message = MessageUtils.message(code, args);
		}
		if (message == null) {
            message = defaultMessage;
        }
		return message;
	}

	@Override
	public String toString() {
		return this.getClass() + "{" +
				"module='" + module + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
	}
}
