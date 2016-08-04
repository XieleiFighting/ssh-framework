package com.hades.ssh.common.entity.search.filter;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * or 条件
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午2:16:06
 * <p>Version: 1.0
 */
public class OrCondition implements SearchFilter {

    private List<SearchFilter> orFilters = Lists.newArrayList();

    OrCondition() {
    }

    public OrCondition add(SearchFilter filter) {
        this.orFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getOrFilters() {
        return orFilters;
    }

    @Override
    public String toString() {
        return "OrCondition{" + orFilters + '}';
    }
}
