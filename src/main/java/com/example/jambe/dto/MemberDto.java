package com.example.jambe.dto;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String account;
    private String name;
    private String email;
    private String passwd;
    private Role role;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .account(account)
                .name(name)
                .email(email)
                .passwd(passwd)
                .role(Role.GUEST)
                .build();
    }
}
