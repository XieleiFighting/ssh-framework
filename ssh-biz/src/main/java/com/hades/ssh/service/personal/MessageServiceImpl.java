package com.hades.ssh.service.personal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hades.ssh.common.service.BaseServiceImpl;
import com.hades.ssh.dao.personal.MessageDao;
import com.hades.ssh.entity.enums.MessageState;
import com.hades.ssh.entity.personal.Message;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {
	
	@Autowired
	private MessageDao messageDao;
	@Override
	public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState) {
		Date changeDate = new Date();
		return messageDao.changeSenderState(senderId, oldState, newState, changeDate);
	}

	@Override
	public Integer changeReceiverState(Long receiverId, MessageState oldState, MessageState newState) {
		Date changeDate = new Date();
        return messageDao.changeReceiverState(receiverId, oldState, newState, changeDate);
	}

	@Override
	public Integer clearDeletedMessage(MessageState deletedState) {
		return messageDao.clearDeletedMessage(deletedState);
	}

	@Override
	public Integer changeState(ArrayList<MessageState> oldStates, MessageState newState, int expireDays) {
		Date changeDate = new Date();
        Integer count = messageDao.changeSenderState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        count += messageDao.changeReceiverState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        return count;
	}

	@Override
	public Long countUnread(Long userId) {
		return messageDao.countUnread(userId, MessageState.in_box);
	}

	@Override
	public void markRead(Long userId, Long[] ids) {
		if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        messageDao.markRead(userId, Arrays.asList(ids));
	}

}
