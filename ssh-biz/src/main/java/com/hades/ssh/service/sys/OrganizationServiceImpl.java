package com.hades.ssh.service.sys;

import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseTreeableServiceImpl;
import com.hades.ssh.entity.sys.Organization;

@Service
public class OrganizationServiceImpl extends BaseTreeableServiceImpl<Organization, Long> implements OrganizationService {

	@Override
	public void filterForCanShow(Set<Long> organizationIds, Set<Long[]> organizationJobIds) {
		Iterator<Long> iter1 = organizationIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            Organization o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        while (iter2.hasNext()) {
            Long id = iter2.next()[0];
            Organization o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter2.remove();
            }
        }
	}

}
