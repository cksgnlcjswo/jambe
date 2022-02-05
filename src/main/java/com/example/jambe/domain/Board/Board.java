package com.example.jambe.domain.Board;

import com.example.jambe.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=50,nullable = false)
    private String category;

    @Builder
    Board(Long id, String category) {
        this.id = id;
        this.category = category;
    }
}
