package com.hades.ssh.service.sys;

import java.util.List;

import com.hades.ssh.common.service.BaseTreeableService;
import com.hades.ssh.entity.sys.Resource;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.entity.tmp.Menu;

public interface ResourceService extends BaseTreeableService<Resource, Long> {

	/**
	 * 得到真实的资源标识  即 父亲:儿子
	 * @param resource
	 * @return
	 */
	String findActualResourceIdentity(Resource resource);
	
	List<Menu> findMenus(User user);
}
