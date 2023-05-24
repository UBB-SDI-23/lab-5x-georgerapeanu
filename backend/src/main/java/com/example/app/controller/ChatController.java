package com.example.app.controller;

import com.example.app.dto.ChatMessageDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.Message;
import com.example.app.model.User;
import com.example.app.repository.MessageRepository;
import com.example.app.repository.UserRepository;
import com.example.app.util.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessageDTO message(
            ChatMessageDTO chatMessageDTO
    ) {
        return chatMessageDTO;
    }

    @GetMapping("/restore-messages")
    public List<ChatMessageDTO> restore_messages(
            @RequestAttribute("user") User user
    ) {
        if(user == null) {
            return new ArrayList<>();
        }
        return messageRepository.findAllByUserHandleOrderById(user.getHandle())
                .stream()
                .map(message -> {
                    return new ChatMessageDTO(message.getNickname(), message.getMessage());
                }).collect(Collectors.toList());
    }

    @PostMapping("/store-messages")
    public void store_messages(
            @RequestAttribute("user") User user,
            @RequestBody ChatMessageDTO messageDTO
    ) {
        if(user == null) {
            return;
        }
        Message message = new Message();
        message.setUser(user);
        message.setMessage(messageDTO.getMessage());
        message.setNickname(messageDTO.getNickname());
        message.setUserHandle(user.getHandle());
        messageRepository.save(message);
    }
}
