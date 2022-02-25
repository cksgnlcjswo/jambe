package com.example.jambe.domain.ChatRoom;

import com.example.jambe.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CHATROOM_ID")
    private Long id;

    @Column(length=40)
    private String name;

    @Builder
    public ChatRoom(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
