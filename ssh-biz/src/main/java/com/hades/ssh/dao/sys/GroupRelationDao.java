/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.dao.sys;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.entity.sys.GroupRelation;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 上午10:58:40
 * <p>Version: 1.0
 */
public interface GroupRelationDao extends BaseDao<GroupRelation, Long> {

    GroupRelation findByGroupIdAndUserId(Long groupId, Long userId);

    /**
     * 范围查 如果在指定范围内 就没必要再新增一个 如当前是[10,20] 如果数据库有[9,21] 10<=9 and 21>=20
     *
     * @param groupId
     * @param startUserId
     * @param endUserId
     * @return
     */
    GroupRelation findByGroupIdAndStartUserIdLessThanEqualAndEndUserIdGreaterThanEqual(Long groupId, Long startUserId, Long endUserId);
    
    /**
     * 删除区间内的数据 因为之前已经有一个区间包含它们了
     *
     * @param startUserId
     * @param endUserId
     */
    @Modifying
    @Query("delete from GroupRelation where (startUserId>=?1 and endUserId<=?2) or (userId>=?1 and userId<=?2)")
    void deleteInRange(Long startUserId, Long endUserId);

    GroupRelation findByGroupIdAndOrganizationId(Long groupId, Long organizationId);

    @Query("select groupId from GroupRelation where userId=?1 or (startUserId<=?1 and endUserId>=?1))")
    List<Long> findGroupIds(Long userId);

    @Query("select groupId from GroupRelation where userId=?1 or (startUserId<=?1 and endUserId>=?1) or (organizationId in (?2))")
    List<Long> findGroupIds(Long userId, Set<Long> organizationIds);


    //无需删除用户 因为用户并不逻辑删除
    @Modifying
    @Query("delete from GroupRelation r where " +
            "not exists (select 1 from Group g where r.groupId = g.id) or " +
            "not exists(select 1 from Organization o where r.organizationId = o.id)")
    void clearDeletedGroupRelation();

}
