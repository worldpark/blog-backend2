package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BoardContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long contentId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Lob
    @Column(columnDefinition = "LONGTEXT", name = "board_content")
    private String boardContentText;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "content_order")
    private Integer contentOrder;

    @Column(nullable = false, name = "content_type")
    private String contentType;

}
