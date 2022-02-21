package com.example.jambe.dto.SubComment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCommentResponseDto {

    String member;
    String content;

    @Builder
    public SubCommentResponseDto(String member, String content) {
        this.member = member;
        this.content = content;
    }
}
