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
    private Board board;
    private Member member;
    private String content;
    private String title;

    @Builder
    public PostResponseDto(Long id, Board board, Member member, String content, String title) {
        this.id =id;
        this.board = board;
        this.member = member;
        this.content = content;
        this.title = title;
    }

}
