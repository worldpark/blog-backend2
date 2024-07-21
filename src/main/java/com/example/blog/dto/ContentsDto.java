package com.example.blog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ContentsDto {

    private final String board_type;
    private final String board_content;
    private final String image_path;
}
