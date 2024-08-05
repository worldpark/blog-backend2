package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;

    @Column(name = "parentId", nullable = true)
    private Long parentId;

    @Column(name="comment_contents")
    private String commentContents;

    @Column(name="comment_password", nullable = false)
    private String commentPassword;

    @Column(name = "create_time", nullable = false)
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "create_id", nullable = false)
    private String createId;

    @Column(name="ip", nullable = false)
    private String ip;

    @Column(name="status", nullable = false)
    private String status;
}
