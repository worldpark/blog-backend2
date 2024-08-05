package com.example.blog.querydsl;

import com.example.blog.dto.HashTagDto;

import java.util.List;

public interface HashTagRepositoryDsl {

    List<HashTagDto> getHashTagList();
}
