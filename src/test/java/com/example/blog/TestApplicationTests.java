package com.example.blog;

import com.example.blog.config.AesUtil;
import com.example.blog.entity.Board;
import com.example.blog.entity.User;
import com.example.blog.querydsl.BoardRepositoryDsl;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BoardRepositoryDsl boardRepositoryDsl;

    @Autowired
    private BoardService boardService;

    private AesUtil aesUtil = new AesUtil();
    
    @Test
    void contextLoads() {
    }

    @Test
    void jpaGetReferenceByIdTest(){

        Optional<User> user = userRepository.findById("testId");

    }

    @Test
    void jpaInsertUser(){
        String pw = bCryptPasswordEncoder.encode("admin22");

        User user = new User("adminId", pw, "ROLE_ADMIN");

        userRepository.save(user);
    }

    @Test
    void getBoardTest(){

        String a = null;

        //BoardContentDto contentResult = boardRepositoryDsl.getBoardContent(100L);

        System.out.println(LocalDateTime.now());

        Board board = new Board();
        board.setBoardId(101L);
        board.setBoardTitle("테스트제목2");
        board.setCreateTime(LocalDateTime.now());

        boardRepository.save(board);

    }

    @Test
    void AesTest(){
        String testStr = "test";

        String encodedStr = aesUtil.aesEncode(testStr);
        String decodedStr = aesUtil.aesDecode(encodedStr);

        System.out.println(encodedStr);
        System.out.println(decodedStr);
    }

}
