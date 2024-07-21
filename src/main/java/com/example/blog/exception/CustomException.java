package com.example.blog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final HttpStatus status;
    private final CustomErrorCode customErrorCode;
    private final String detail;

}
