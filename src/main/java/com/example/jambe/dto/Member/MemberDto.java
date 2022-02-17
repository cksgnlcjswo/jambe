package com.example.jambe.dto.Member;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import com.example.jambe.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String account;
    private String name;
    private String nickname;
    private String email;
    private String passwd;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .account(account)
                .name(name)
                .nickname(nickname)
                .email(email)
                .passwd(passwd)
                .build();
    }
}
