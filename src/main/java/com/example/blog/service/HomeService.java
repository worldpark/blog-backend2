package com.example.blog.service;

import com.example.blog.dto.UserAuthDto;
import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final Logger logger = LoggerFactory.getLogger(HomeService.class);

    public UserAuthDto sessionCheck(Principal principal){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String userId = principal.getName();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            List<String> auths = new ArrayList<>();
            for(GrantedAuthority authority : authorities){
                auths.add(authority.getAuthority());
            }

            UserAuthDto userAuthDto = new UserAuthDto(
                    userId,
                    auths
            );

            return userAuthDto;
        }catch (NullPointerException nullException){
            throw new CustomException(HttpStatus.OK, CustomErrorCode.INVALID_SESSION, "비 유효 로그인 세션");
        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }

    }


}