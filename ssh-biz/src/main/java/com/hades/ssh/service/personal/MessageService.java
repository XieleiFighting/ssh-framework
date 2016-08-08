package com.hades.ssh.service.personal;

import java.util.ArrayList;

import com.hades.ssh.common.service.BaseService;
import com.hades.ssh.entity.enums.MessageState;
import com.hades.ssh.entity.personal.Message;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午5:07:44
 * <p>Version: 1.0
 */
public interface MessageService extends BaseService<Message, Long> {

	Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState);
	
	Integer changeReceiverState(Long receiverId, MessageState oldState, MessageState newState);
	
	Integer clearDeletedMessage(MessageState deletedState);
	
	Integer changeState(ArrayList<MessageState> oldStates, MessageState newState, int expireDays);
	
	Long countUnread(Long userId);
	
	void markRead(final Long userId, final Long[] ids);
}
