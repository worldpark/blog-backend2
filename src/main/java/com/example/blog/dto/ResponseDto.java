package com.example.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ResponseDto {

    private final String code;
    private final String message;
}
