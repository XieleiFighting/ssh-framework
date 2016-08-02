package com.hades.ssh.common.service;

import java.io.Serializable;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.common.entity.AbstractEntity;

public class BaseServiceImpl<T extends AbstractEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

	protected BaseDao<T, ID> baseDao;
}
