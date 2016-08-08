package com.hades.ssh.service.sys;

import java.util.Set;

import com.hades.ssh.common.service.BaseTreeableService;
import com.hades.ssh.entity.sys.Job;

public interface JobService extends BaseTreeableService<Job, Long> {

	void filterForCanShow(Set<Long> jobIds, Set<Long[]> organizationJobIds);
}
