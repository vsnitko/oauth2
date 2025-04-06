package com.vsnitko.oauth2.model.mapper;

import com.vsnitko.oauth2.model.entity.Chat;
import com.vsnitko.oauth2.model.payload.ChatInfoResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author v.snitko
 * @since 2025.03.31
 */
@Component
public class ChatMapper {
    public ChatInfoResponse toDto(Chat chat) {
        final String lastMessage = chat.getMessages().isEmpty()
                ? null
                : chat.getMessages().getLast().getContent();
        return new ChatInfoResponse()
                .setId(chat.getId())
                .setName(chat.getName())
                .setAvatar(chat.getAvatar())
                .setLastMessage(lastMessage);
    }
}
