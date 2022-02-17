package com.example.jambe.dto.Post;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Member.Member;
import com.example.jambe.domain.Post.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String board;
    private String member;
    private String content;
    private String title;

    @Builder
    public PostResponseDto(Long id, String board, String member, String content, String title) {
        this.id =id;
        this.board = board;
        this.member = member;
        this.content = content;
        this.title = title;
    }

}
