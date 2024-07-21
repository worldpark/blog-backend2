package com.example.blog.repository;

import com.example.blog.entity.Board;
import com.example.blog.entity.BoardAndHash;
import com.example.blog.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardAndHashRepository extends JpaRepository<BoardAndHash, Long> {

    void deleteByBoard(Board board);

    void deleteByHashTag(HashTag hashTag);
}
