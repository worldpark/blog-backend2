package com.example.blog.controller;

import com.example.blog.dto.ResponseDto;
import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorDto;
import com.example.blog.service.HomeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Tag(name = "Home", description = "공통적으로 사용되는 작업 처리")
@ApiResponse(responseCode = "400", description = "필요 데이터를 전달받지 못하였거나, NullException 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "500", description = "JPA 예외 혹은 기타 예외 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "200", description = "데이터를 정상적으로 처리")
public class HomeController {

    private final HomeService homeService;

    @Hidden
    @GetMapping("/")
    public String rootPage(){
        return "rootPage";
    }

    @Operation(summary = "권한 부족 처리", description = "권한 부족 처리")
    @GetMapping("/access-denied")
    public void accessDenied(){

        throw new CustomException(HttpStatus.METHOD_NOT_ALLOWED, CustomErrorCode.INVALID_AUTH, "해당 권한이 없습니다.");
    }

    @Operation(summary = "권한 체크", description = "권한 체크")
    @PostMapping("/check-session")
    public HashMap<String, Object> authenticateUser(Principal principal){

        return homeService.sessionCheck(principal);
    }


}
