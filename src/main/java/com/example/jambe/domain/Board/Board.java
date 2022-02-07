package com.example.jambe.domain.Board;

import com.example.jambe.domain.BaseTimeEntity;
import com.example.jambe.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_ID")
    private Long id;

    @Column(length=50,nullable = false)
    private String category;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Builder
    Board(Long id,
          String category) {

        this.id = id;
        this.category = category;
    }


}
