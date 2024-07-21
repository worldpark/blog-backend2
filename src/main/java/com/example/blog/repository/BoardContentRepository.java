package com.example.blog.repository;

import com.example.blog.entity.Board;
import com.example.blog.entity.BoardContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardContentRepository extends JpaRepository<BoardContent, Long> {

    List<BoardContent> findByBoardOrderByContentOrder(Board board);

    void deleteByBoard(Board board);
}
