package com.hades.ssh.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * 如果是oracle请参考{@link BaseOracleEntity}
 * <p>User: XieLei
 * <p>Date: 2016年8月2日 下午6:10:46
 * <p>Version: 1.0
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

	private static final long serialVersionUID = 423439112529888374L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private ID id;
	
	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setId(ID id) {
		this.id = id;
	}

}
