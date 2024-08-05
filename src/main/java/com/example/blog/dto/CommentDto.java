package com.example.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@Builder
public class CommentDto {

    private final Long commentId;
    private final Long parentId;
    private final String commentContents;
    private final LocalDateTime createTime;
    private final String createId;
    private final String ip;
    private final String status;
}
