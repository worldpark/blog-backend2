package com.example.blog.repository;

import com.example.blog.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    HashTag findByHashName(String hash_name);
}
