package com.example.blog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardHashTagDto {

    private final Long board_hash_id;
    private final String hash_name;
}
