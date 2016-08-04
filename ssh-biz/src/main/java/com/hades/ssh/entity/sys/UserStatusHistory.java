/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.hades.ssh.common.entity.BaseEntity;
import com.hades.ssh.entity.enums.UserStatus;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:23
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_user_status_history")
public class UserStatusHistory extends BaseEntity<Long> {

	private static final long serialVersionUID = -5160798903194261463L;

	/**
     * 锁定的用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private User user;

    /**
     * 备注信息
     */
    private String reason;

    /**
     * 操作的状态
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /**
     * 操作的管理员
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "op_user_id")
    private User opUser;

    /**
     * 操作时间
     */
    @Column(name = "op_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opDate;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}
