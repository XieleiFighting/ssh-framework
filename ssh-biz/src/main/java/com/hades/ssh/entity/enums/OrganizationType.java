package com.hades.ssh.entity.enums;

/**
 * 组织机构类型
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午5:52:50
 * <p>Version: 1.0
 */
public enum OrganizationType {
    bloc("集团"), branch_office("分公司"), department("部门"), group("部门小组");

    private final String info;

    private OrganizationType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
