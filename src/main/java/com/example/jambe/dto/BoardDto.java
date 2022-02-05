package com.example.jambe.dto;

import com.example.jambe.domain.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String category;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .category(category)
                .build();
    }
}
