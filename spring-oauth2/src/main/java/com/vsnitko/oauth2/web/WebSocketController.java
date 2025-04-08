package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.MessageRequest;
import com.vsnitko.oauth2.model.payload.MessageResponse;
import com.vsnitko.oauth2.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.SESSION_ATTRIBUTES;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final MessageService messageService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(
            MessageRequest message,
            @Header(SESSION_ATTRIBUTES) Map<String, Object> attributes
    ) {
        final User user = (User) attributes.get("user");
        if (user != null) {
            return messageService.sendMessage(message, user);
        }
        return null;
    }
}
