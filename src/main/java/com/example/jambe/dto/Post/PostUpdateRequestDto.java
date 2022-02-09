package com.example.jambe.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class PostUpdateRequestDto {

    private String title;
    private String content;
}
