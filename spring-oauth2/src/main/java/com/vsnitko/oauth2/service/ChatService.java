package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.config.AppProperties;
import com.vsnitko.oauth2.model.entity.Chat;
import com.vsnitko.oauth2.model.mapper.ChatMapper;
import com.vsnitko.oauth2.model.payload.ChatInfoResponse;
import com.vsnitko.oauth2.model.payload.MessageResponse;
import com.vsnitko.oauth2.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author v.snitko
 * @since 2025.03.22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final AppProperties appProperties;
    private final S3Service s3Service;

    public List<ChatInfoResponse> getChatList() {
        return chatRepository.findAll().stream()
                .map(chatMapper::toDto)
                .toList();
    }

    public ChatInfoResponse getChatInfo(Long id) {
        final Chat chat = chatRepository.findById(id).orElse(null);
        if (chat == null) {
            return null;
        }
        return ChatInfoResponse.builder()
                .avatar(chat.getAvatar())
                .name(chat.getName())
                .build();
    }

    public List<MessageResponse> getChatMessages(Long id) {
        final Chat chat = chatRepository.findById(id).orElse(null);
        if (chat == null) {
            return null;
        }
        return chat.getMessages().stream().map(
                message -> MessageResponse.builder()
                        .senderId(message.getSender().getId())
                        .senderName(message.getSender().getName())
                        .avatar(message.getSender().getAvatar())
                        .messageText(message.getContent())
                        .build()
        ).toList();
    }

    public ChatInfoResponse createChat(String roomName, MultipartFile avatar) {
        try {
            final String avatarFileName;
            if (avatar != null) {
                avatarFileName = UUID.randomUUID() + avatar.getOriginalFilename();

                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(avatar.getInputStream())
                        .crop(Positions.CENTER)
                        .size(162, 162)
                        .toOutputStream(outputStream);
                s3Service.uploadFile(avatarFileName, outputStream.toByteArray());

            } else {
                avatarFileName = null;
            }
            
            Chat chat = new Chat()
                    .setName(roomName)
                    .setAvatar(avatarFileName);
            final Chat savedChat = chatRepository.save(chat);
            return chatMapper.toDto(savedChat);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
