package com.vsnitko.oauth2.model.mapper;

import com.vsnitko.oauth2.model.entity.Message;
import com.vsnitko.oauth2.model.payload.MessageResponse;
import org.springframework.stereotype.Component;

/**
 * @author v.snitko
 * @since 2025.04.08
 */
@Component
public class MessageMapper {

    public MessageResponse toDto(Message message) {

        return MessageResponse.builder()
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getName())
                .avatar(message.getSender().getAvatar())
                .messageText(message.getContent())
                .build();
    }
}
