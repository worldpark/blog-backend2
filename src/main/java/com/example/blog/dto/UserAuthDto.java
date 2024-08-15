package com.example.blog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class UserAuthDto {

    private final String userId;
    private final List<String> auths;
}
