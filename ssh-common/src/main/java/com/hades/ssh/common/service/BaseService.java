package com.hades.ssh.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hades.ssh.common.entity.AbstractEntity;
import com.hades.ssh.common.entity.search.Searchable;

public interface BaseService<T extends AbstractEntity<ID>, ID extends Serializable> {

	/**
	 * 保存单个实体
	 * @param entity
	 * @return
	 */
	T save(T entity);
	
	T saveAndFlush(T entity);
	
	T update(T entity);
	
	/**
	 * 根据主键删除相应实体
	 * @param id
	 */
	void delete(ID id);
	
	void delete(T entity);
	
	void delete(ID[] ids);
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 返回id对应的实体
	 */
	T findOne(ID id);
	
	boolean exists(ID id);
	
	long count();
	
	/**
     * 查询所有实体
     *
     * @return
     */
	List<T> findAll();
	
	/**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
	List<T> findAll(Sort sort);
	
	/**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
	Page<T> findAll(Pageable pageable);
	
	/**
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
	Page<T> findAll(Searchable searchable);
	
	/**
     * 按条件不分页不排序查询实体
     *
     * @param searchable 条件
     * @return
     */
	List<T> findAllWithNoPageNoSort(Searchable searchable);
	
	/**
     * 按条件排序查询实体(不分页)
     *
     * @param searchable 条件
     * @return
     */
	List<T> findAllWithSort(Searchable searchable);
	
	/**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
	Long count(Searchable searchable);
}
