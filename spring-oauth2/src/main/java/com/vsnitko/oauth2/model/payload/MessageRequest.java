package com.vsnitko.oauth2.model.payload;

import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Data
@Builder
public class MessageRequest {
    
    private Long chatId;
    private String messageText;
}
