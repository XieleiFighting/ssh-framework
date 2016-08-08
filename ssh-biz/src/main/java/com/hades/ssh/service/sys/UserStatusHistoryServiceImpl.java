package com.hades.ssh.service.sys;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.sys.UserStatusHistoryDao;
import com.hades.ssh.entity.enums.UserStatus;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.entity.sys.UserStatusHistory;

@Service
public class UserStatusHistoryServiceImpl extends BaseServiceImpl<UserStatusHistory, Long> implements UserStatusHistoryService {

	@Autowired
	private UserStatusHistoryDao userStatusHistoryDao;
	
	@Override
	public void log(User opUser, User user, UserStatus newStatus, String reason) {
		UserStatusHistory history = new UserStatusHistory();
        history.setUser(user);
        history.setOpUser(opUser);
        history.setOpDate(new Date());
        history.setStatus(newStatus);
        history.setReason(reason);
        save(history);
	}

	@Override
	public UserStatusHistory findLastHistory(User user) {
		Searchable searchable = Searchable.newSearchable()
                .addSearchParam("user_eq", user)
                .addSort(Sort.Direction.DESC, "opDate")
                .setPage(0, 1);

        Page<UserStatusHistory> page = userStatusHistoryDao.findAll(searchable);

        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
	}

	@Override
	public String getLastReason(User user) {
		UserStatusHistory history = findLastHistory(user);
        if (history == null) {
            return "";
        }
        return history.getReason();
	}

}
