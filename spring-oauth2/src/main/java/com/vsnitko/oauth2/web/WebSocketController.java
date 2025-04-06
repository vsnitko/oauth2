package com.vsnitko.oauth2.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Controller
public class WebSocketController {

    @MessageMapping("/chat.sendMessage") 
    @SendTo("/topic/public") 
    public String sendMessage(String message) {
        return message;
    }
}
