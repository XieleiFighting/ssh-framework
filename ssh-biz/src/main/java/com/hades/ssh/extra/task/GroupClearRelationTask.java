/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.extra.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.dao.sys.GroupRelationDao;

/**
 * 清理无关联的关系
 * 1、Group-GroupRelation
 * 2、GroupRelation-User
 * 3、GroupRelation-Organization
 * 4、GroupRelation-Job
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-13 下午5:35
 * <p>Version: 1.0
 */
@Service
public class GroupClearRelationTask {

    @Autowired
    private GroupRelationDao groupRelationDao;

    /**
     * 清除删除的分组对应的关系
     */
    public void clearDeletedGroupRelation() {
    	groupRelationDao.clearDeletedGroupRelation();
    }

}
