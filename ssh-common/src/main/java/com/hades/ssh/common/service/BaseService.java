package com.hades.ssh.common.service;

import java.io.Serializable;

import com.hades.ssh.common.entity.AbstractEntity;

public interface BaseService<T extends AbstractEntity<ID>, ID extends Serializable> {

}
