package com.example.app.controller;

import com.example.app.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessageDTO message(ChatMessageDTO chatMessageDTO) {
        return chatMessageDTO;
    }
}
