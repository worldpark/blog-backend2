package com.example.blog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 614242838L;

    public static final QBoard board = new QBoard("board");

    public final ListPath<BoardAndHash, QBoardAndHash> boardAndHash = this.<BoardAndHash, QBoardAndHash>createList("boardAndHash", BoardAndHash.class, QBoardAndHash.class, PathInits.DIRECT2);

    public final ListPath<BoardContent, QBoardContent> boardContent = this.<BoardContent, QBoardContent>createList("boardContent", BoardContent.class, QBoardContent.class, PathInits.DIRECT2);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

