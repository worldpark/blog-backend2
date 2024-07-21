package com.example.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT A.board_id, " +
            "CASE WHEN content_order = (SELECT MIN(B.content_order)" +
                                        " FROM board_content B" +
                                        " WHERE B.board_id = A.board_id" +
                                            " AND B.content_type = 'text') THEN A.board_content END AS preview_content" +
            " FROM board_content A" +
            " WHERE A.content_type = 'text'" +
            " GROUP BY A.board_id")
@Data
@NoArgsConstructor
public class BoardTextContentSub {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "preview_content")
    private String previewContent;

}
