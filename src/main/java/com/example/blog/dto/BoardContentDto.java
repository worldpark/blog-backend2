package com.example.blog.dto;

import com.example.blog.entity.BoardContent;
import com.example.blog.entity.HashTag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class BoardContentDto {

    private final Long board_id;
    private final String board_title;
    private final LocalDateTime create_time;

    private List<BoardHashTagDto> hashTags;
    private List<ContentsDto> board_content_list;
}
