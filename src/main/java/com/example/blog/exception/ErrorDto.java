package com.example.blog.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
@RequiredArgsConstructor
public class ErrorDto {

    private final String code;
    private final String message;
    private final String detail;

    public static ResponseEntity<ErrorDto> errorDtoResponseEntity(CustomException customException){
        CustomErrorCode customErrorCode = customException.getCustomErrorCode();
        String detail = customException.getDetail();

        return ResponseEntity
                .status(customException.getStatus())
                .body(ErrorDto.builder()
                        .code(customErrorCode.getCode())
                        .message(customErrorCode.getMessage())
                        .detail(detail)
                        .build());
    }

}
