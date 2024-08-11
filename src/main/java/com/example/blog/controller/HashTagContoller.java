package com.example.blog.controller;

import com.example.blog.dto.HashTagDto;
import com.example.blog.dto.ResponseDto;
import com.example.blog.exception.ErrorDto;
import com.example.blog.service.HashTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "HashTag", description = "해시태그과 관련된 처리")
@ApiResponse(responseCode = "400", description = "필요 데이터를 전달받지 못하였거나, NullException 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "500", description = "JPA 예외 혹은 기타 예외 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "200", description = "데이터를 정상적으로 처리")
public class HashTagContoller {

    private final HashTagService hashTagService;

    @Operation(summary = "해시태그 리스트 출력", description = "등록된 해시태그 리스트 출력")
    @PostMapping("/getHashTagList")
    public List<HashTagDto> getHashTagList() {

        return hashTagService.getHashTagList();
    }

    @Operation(summary = "해시태그 등록", description = "해시태그 등록")
    @PostMapping("/setHashTag")
    public String setHashTag(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "해시태그 등록",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"hash_label\": \"해시태그 이름\"}"
                            )
                    ))
            @RequestBody HashMap<String, String> param) {

        hashTagService.setHashTag(param.get("hash_label"));

        return "해시태그 생성 완료";
    }

    @Operation(summary = "해시태그 삭제", description = "해시태그 삭제")
    @DeleteMapping("/hashTag")
    public ResponseEntity<ResponseDto> deleteHashTag(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "해시태그 삭제",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"hashId\": \"해시태그 ID\"}"
                            )
                    ))
            @RequestBody HashMap<String, String> param){

        return hashTagService.deleteHashTag(Long.parseLong(param.get("hashId")));

    }
}
