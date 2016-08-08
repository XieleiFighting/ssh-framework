package com.hades.ssh.common.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import com.hades.ssh.common.entity.BaseEntity;
import com.hades.ssh.common.plugin.entity.Movable;

public interface BaseMovableService<T extends BaseEntity<ID> & Movable, ID extends Serializable> extends BaseService<T, ID>  {

	/**
     * 按照降序进行移动
     * 把{fromId}移动到{}toId}之后
     * 如 fromWeight 2000 toWeight 1000   则新的为 500
     * @param fromId
     * @param toId
     */
	void down(ID fromId, ID toId);
	
	/**
     * 按照降序进行移动
     * 把{fromId}移动到toId之下
     * 如 fromWeight 1000 toWeight 2000  3000 则新的为 2500
     *
     * @param fromId
     * @param toId
     */
	void up(ID fromId, ID toId);
	
	void reweight();
	
	void doReweight(Page<T> page);
	
	T findPreByWeight(Integer weight);
	
	T findNextByWeight(Integer weight);
}
