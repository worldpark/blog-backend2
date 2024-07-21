package com.example.blog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String user_id = (String) authentication.getPrincipal();
        String user_pw = (String) authentication.getCredentials();

        CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user_id);

        if(!bCryptPasswordEncoder.matches(user_pw, user.getPassword())){
            throw new BadCredentialsException("PW not equals");
        }

        if(!user.isEnabled()){
            throw new BadCredentialsException("ID is not Enabled");
        }

        return new UsernamePasswordAuthenticationToken(user_id, user_pw, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
