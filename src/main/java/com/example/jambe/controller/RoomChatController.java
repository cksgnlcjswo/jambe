package com.example.jambe.controller;

import com.example.jambe.domain.ChatRoom.ChatRoom;
import com.example.jambe.domain.ChatRoom.ChatRoomRepository;
import com.example.jambe.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class RoomChatController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/chat/rooms")
    public String allRooms(Model model) {
        log.info("All chat rooms");
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        model.addAttribute("chatRoomList",chatRoomList);

        return "chatRoom";
    }

    @PostMapping("/chat/room")
    public String create(@RequestBody ChatRoomDto chatRoomDto) {
        log.info("Create Chat room, name : {}",chatRoomDto.getName());

        chatRoomRepository.save(chatRoomDto.toEntity(chatRoomDto));

        return "redirect:/chat/rooms";
    }

    @GetMapping("/chat/room/")
    public String getRoom(Long id,Model model) {
        log.info("get Chat room, room id : {}", id);
        ChatRoom chatRoom = chatRoomRepository.findById(id).get();
        model.addAttribute("chatRoom",chatRoom);

        return "chat";
    }
}
