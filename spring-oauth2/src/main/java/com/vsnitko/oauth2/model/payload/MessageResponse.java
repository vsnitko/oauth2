package com.vsnitko.oauth2.model.payload;

import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Data
@Builder
public class MessageResponse {
    private Long senderId;
    private String senderName;
    private String avatar;
    private String messageText;
}
