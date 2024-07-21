package com.example.blog.dto;

import com.example.blog.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String user_id;
    private String user_pw;
    private String user_auth;

    public UserDto(User user){
        this.user_id = user.getUserId();
        this.user_pw = user.getUserPw();
        this.user_auth = user.getUserAuth();
    }
}
