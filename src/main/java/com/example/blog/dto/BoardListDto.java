package com.example.blog.dto;

import com.example.blog.entity.HashTag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardListDto {

    private final Long board_id;
    private final String board_title;
    private String preview_content;

    private List<HashTag> hashTags;

}
