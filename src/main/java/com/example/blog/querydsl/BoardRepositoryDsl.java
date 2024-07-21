package com.example.blog.querydsl;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.BoardAndHash;

import java.util.HashMap;
import java.util.List;

public interface BoardRepositoryDsl {

    public List<BoardDto> getBoardList(String hashTagName, Long pageNumber);

    public BoardContentDto getBoardContent(Long boardId);
}
