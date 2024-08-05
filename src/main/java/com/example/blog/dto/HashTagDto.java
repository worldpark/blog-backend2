package com.example.blog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HashTagDto {

    private final Long hashId;
    private final String hashLabel;
    private final Long boardCount;
}
