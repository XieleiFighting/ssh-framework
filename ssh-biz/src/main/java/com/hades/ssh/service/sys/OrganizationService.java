package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.common.service.BaseTreeableService;
import com.hades.ssh.entity.sys.Organization;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 上午10:25:37
 * <p>Version: 1.0
 */
public interface OrganizationService extends BaseTreeableService<Organization, Long> {

	/**
	 * 过滤仅获取可显示的数据
	 * @param organizationIds
	 * @param organizationJobIds
	 */
	void filterForCanShow(Set<Long> organizationIds, Set<Long[]> organizationJobIds);
	
	
}
