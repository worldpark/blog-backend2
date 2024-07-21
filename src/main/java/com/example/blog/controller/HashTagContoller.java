package com.example.blog.controller;

import com.example.blog.dto.HashTagDto;
import com.example.blog.dto.ResponseDto;
import com.example.blog.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HashTagContoller {

    private final HashTagService hashTagService;

    @PostMapping("/getHashTagList")
    public List<HashTagDto> getHashTagList() {

        return hashTagService.getHashTagList();
    }

    @PostMapping("/setHashTag")
    public String setHashTag(@RequestParam HashMap<String, String> param) {

        hashTagService.setHashTag(param.get("hash_label"));

        return "해시태그 생성 완료";
    }

    @DeleteMapping("/hashTag")
    public ResponseEntity<ResponseDto> deleteHashTag(@RequestParam HashMap<String, String> param){

        return hashTagService.deleteHashTag(Long.parseLong(param.get("hashId")));

    }
}
