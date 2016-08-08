package com.hades.ssh.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.hades.ssh.common.entity.BaseEntity;
import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.plugin.entity.Treeable;

public interface BaseTreeableService<T extends BaseEntity<ID> & Treeable<ID>, ID extends Serializable> extends BaseService<T, ID> {

	void deleteSelfAndChild(T m);
	
	void deleteSelfAndChild(List<T> mList);
	
	void appendChild(T parent, T child);
	
	int nextWeight(ID id);
	
	/**
     * 移动节点
     * 根节点不能移动
     *
     * @param source   源节点
     * @param target   目标节点
     * @param moveType 位置
     */
	void move(T source, T target, String moveType);
	
//	List<T> findSelfAndNextSiblings(String parentIds, int currentWeight);
	
	Set<String> findNames(Searchable searchable, String name, ID excludeId);
	
	List<T> findChildren(List<T> parents, Searchable searchable);
	
	List<T> findAllByName(Searchable searchable, T excludeM);
	
	List<T> findRootAndChild(Searchable searchable);
	
	Set<ID> findAncestorIds(Iterable<ID> currentIds);
	
	Set<ID> findAncestorIds(ID currentId);
	
	List<T> findAncestor(String parentIds);
	
	void addExcludeSearchFilter(Searchable searchable, T excludeM);
	
}
