package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.Chat;
import com.vsnitko.oauth2.model.entity.Message;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.mapper.MessageMapper;
import com.vsnitko.oauth2.model.payload.MessageRequest;
import com.vsnitko.oauth2.model.payload.MessageResponse;
import com.vsnitko.oauth2.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author v.snitko
 * @since 2025.04.08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageResponse sendMessage(MessageRequest message, User principal) {
        log.info("Message sent: {} {}", message, principal);
        final Message messageToSave = new Message()
                .setContent(message.getMessageText())
                .setChat(new Chat().setId(message.getChatId()))
                .setSender(principal);
        final Message savedMessage = messageRepository.save(messageToSave);
        return messageMapper.toDto(savedMessage);
    }

}
