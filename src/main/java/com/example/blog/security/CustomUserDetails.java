package com.example.blog.security;

import com.example.blog.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String user_id;
    private final String user_pw;
    private final String user_auth;

    public CustomUserDetails(User user){
        this.user_id = user.getUserId();
        this.user_pw = user.getUserPw();
        this.user_auth = user.getUserAuth();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user_auth));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user_pw;
    }

    @Override
    public String getUsername() {
        return this.user_id;
    }
}
