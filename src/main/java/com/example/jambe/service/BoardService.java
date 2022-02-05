package com.example.jambe.service;

import com.example.jambe.domain.Board.Board;
import com.example.jambe.domain.Board.BoardRepository;
import com.example.jambe.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Long save(BoardDto boardDto) {
        return boardRepository.findByCategory(boardDto.getCategory())
                .orElseGet( () -> boardRepository.save(boardDto.toEntity())).getId();
    }

    public List<Board> findAll() {

        return boardRepository.findAll();
    }
}
