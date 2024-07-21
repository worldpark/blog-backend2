package com.example.blog.security;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UsernameNotFoundException("not found ID"));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return userDetails;
    }
}
