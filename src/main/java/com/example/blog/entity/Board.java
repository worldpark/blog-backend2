package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "create_time")
    @CreatedDate
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "board")
    private List<BoardAndHash> boardAndHash;

    @OneToMany(mappedBy = "board")
    private List<BoardContent> boardContent;


}
