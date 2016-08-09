package com.hades.ssh.service.maintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.maintain.KeyValueDao;
import com.hades.ssh.entity.maintain.KeyValue;

@Service
public class KeyValueServiceImpl extends BaseServiceImpl<KeyValue, Long> implements KeyValueService {

	@Autowired
	private KeyValueDao keyValueDao;
	
	@Override
	public KeyValue findByKey(String key) {
		return keyValueDao.findByKey(key);
	}

}
