package com.example.jambe.dto.chat;

import com.example.jambe.domain.ChatRoom.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDto {

    private Long roomId;
    private String name;

    public ChatRoom toEntity(ChatRoomDto chatRoomDto) {
        return ChatRoom.builder()
                .id(chatRoomDto.getRoomId())
                .name(chatRoomDto.getName()).build();
    }
}
