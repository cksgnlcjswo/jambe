package com.example.jambe.dto.Member;

import com.example.jambe.domain.Member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {

    private String name;
    private String account;
    private String email;

    @Builder
    public MemberResponseDto(String name,String account,String email) {
        this.name = name;
        this.account = account;
        this.email = email;
    }
}
