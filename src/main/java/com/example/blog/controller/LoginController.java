package com.example.blog.controller;

import com.example.blog.dto.ResponseDto;
import com.example.blog.exception.ErrorDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class LoginController {

    @GetMapping("/login/fail")
    public ResponseEntity<ErrorDto> loginFail(@RequestParam(value = "errorCode", required = false) String errorCode,
                                    @RequestParam(value = "message", required = false) String errorMessage){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .code(errorCode)
                        .message(errorMessage)
                        .detail(errorMessage)
                        .build());

    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<ResponseDto> loginSuccess(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .code("200")
                        .message("로그인 성공")
                        .build());

    }
}
