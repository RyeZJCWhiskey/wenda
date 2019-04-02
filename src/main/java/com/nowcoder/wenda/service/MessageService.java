package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.MessageDao;
import com.nowcoder.wenda.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private SensitiveService sensitiveService;

    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getConvesationUnreadCount(int userId, String conversationId){
        return messageDao.getConvesationUnreadCount(userId, conversationId);
    }
}
