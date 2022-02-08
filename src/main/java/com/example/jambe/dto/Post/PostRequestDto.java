package com.example.jambe.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostRequestDto {

    private Long id;
    private Long board;
    private Long member;
    private String content;
    private String title;
}
