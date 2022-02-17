package com.example.jambe.dto.Comment;

import com.example.jambe.domain.Member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private String content;
    private String member;

    @Builder
    public CommentResponseDto(String content,String member) {
        this.content = content;
        this.member = member;
    }
}
