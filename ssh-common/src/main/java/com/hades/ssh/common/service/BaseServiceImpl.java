package com.hades.ssh.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Lists;
import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.common.entity.AbstractEntity;
import com.hades.ssh.common.entity.search.Searchable;

public class BaseServiceImpl<T extends AbstractEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
	protected BaseDao<T, ID> baseDao;

	@Override
	public T save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public T saveAndFlush(T entity) {
		entity = save(entity);
		baseDao.flush();
		return entity;
	}

	@Override
	public T update(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public void delete(ID id) {
		baseDao.delete(id);
	}

	@Override
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(ID[] ids) {
		baseDao.delete(ids);
	}

	@Override
	public T findOne(ID id) {
		return baseDao.findOne(id);
	}

	@Override
	public boolean exists(ID id) {
		return baseDao.exists(id);
	}

	@Override
	public long count() {
		return baseDao.count();
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return baseDao.findAll(sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return baseDao.findAll(pageable);
	}

	@Override
	public Page<T> findAll(Searchable searchable) {
		return baseDao.findAll(searchable);
	}

	@Override
	public List<T> findAllWithNoPageNoSort(Searchable searchable) {
		searchable.removePageable();
        searchable.removeSort();
		return Lists.newArrayList(baseDao.findAll(searchable).getContent());
	}

	@Override
	public List<T> findAllWithSort(Searchable searchable) {
		searchable.removePageable();
		return Lists.newArrayList(baseDao.findAll(searchable).getContent());
	}

	@Override
	public Long count(Searchable searchable) {
		return baseDao.count(searchable);
	}
}
