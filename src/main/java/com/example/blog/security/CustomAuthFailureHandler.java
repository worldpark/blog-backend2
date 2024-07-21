package com.example.blog.security;

import com.example.blog.exception.CustomErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        String errorCode;

        if(exception instanceof BadCredentialsException){
            errorMessage = CustomErrorCode.DIFFERENCE_ID_OR_PW.getMessage();
            errorCode = CustomErrorCode.DIFFERENCE_ID_OR_PW.getCode();

        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMessage = CustomErrorCode.SERVER_ERROR.getMessage();
            errorCode = CustomErrorCode.SERVER_ERROR.getCode();

        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = CustomErrorCode.NOT_EXISTS_ID.getMessage();
            errorCode = CustomErrorCode.NOT_EXISTS_ID.getCode();

        }else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage = CustomErrorCode.REJERCT_AUTH.getMessage();
            errorCode = CustomErrorCode.REJERCT_AUTH.getCode();

        }else{
            errorMessage = CustomErrorCode.UNKNOWN_ERROR.getMessage();
            errorCode = CustomErrorCode.UNKNOWN_ERROR.getCode();
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login/fail?errorCode=" + errorCode + "&message="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
