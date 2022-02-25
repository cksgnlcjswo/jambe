package com.example.jambe.controller;

import com.example.jambe.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto chatMessageDto) {
        chatMessageDto.setMessage(chatMessageDto.getWriter()+"님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(),chatMessageDto);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto chatMessageDto) {
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }
}
