package com.example.blog.controller;

import com.example.blog.dto.ResponseDto;
import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import com.example.blog.service.HomeService;
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
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public String rootPage(){
        return "rootPage";
    }

    @GetMapping("/access-denied")
    public void accessDenied(){

        throw new CustomException(HttpStatus.METHOD_NOT_ALLOWED, CustomErrorCode.INVALID_AUTH, "해당 권한이 없습니다.");
    }

    @PostMapping("/check-session")
    public HashMap<String, Object> authenticateUser(Principal principal){

        return homeService.sessionCheck(principal);
    }


}
