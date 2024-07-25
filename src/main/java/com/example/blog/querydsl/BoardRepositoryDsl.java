package com.example.blog.querydsl;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardListDto;

import java.util.List;

public interface BoardRepositoryDsl {

    public List<BoardListDto> getBoardList(String hashTagName, Long pageNumber);

    public BoardContentDto getBoardContent(Long boardId);
}
