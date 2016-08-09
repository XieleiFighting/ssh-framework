package com.hades.ssh.service.maintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.maintain.IconDao;
import com.hades.ssh.entity.maintain.Icon;

@Service
public class IconServiceImpl extends BaseServiceImpl<Icon, Long> implements IconService {

	@Autowired
	private IconDao iconDao;
	
	@Override
	public Icon findByIdentity(String identity) {
        return iconDao.findByIdentity(identity);
    }
}
