package com.example.jambe.dto.SubComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCommentRequestDto {

    Long id;
    Long comment;
    String content;
}
