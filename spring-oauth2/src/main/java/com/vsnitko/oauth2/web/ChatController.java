package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.model.payload.ChatInfoResponse;
import com.vsnitko.oauth2.model.payload.MessageResponse;
import com.vsnitko.oauth2.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/{id}/info")
    public ChatInfoResponse getChatInfo(@PathVariable Long id) {
        return chatService.getChatInfo(id);
    }

    @GetMapping("/chat/{id}/messages")
    public List<MessageResponse> getChatMessages(@PathVariable Long id) {
        return chatService.getChatMessages(id);
    }

    @GetMapping("/chats")
    public List<ChatInfoResponse> getChats() {
        return chatService.getChatList();
    }


    @PostMapping("/chat/create")
    public ResponseEntity<ChatInfoResponse> createChat(
            @RequestParam("roomName") String roomName,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar
    ) {
        return ResponseEntity.ok(chatService.createChat(roomName, avatar));
    }
}
