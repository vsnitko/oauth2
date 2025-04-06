package com.vsnitko.oauth2.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatInfoResponse {
    private Long id;
    private String name;
    private String avatar;
    private String lastMessage;
}
