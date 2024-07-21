package com.example.blog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardContent is a Querydsl query type for BoardContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardContent extends EntityPathBase<BoardContent> {

    private static final long serialVersionUID = -1305752189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardContent boardContent = new QBoardContent("boardContent");

    public final QBoard board;

    public final StringPath boardContentText = createString("boardContentText");

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final NumberPath<Integer> contentOrder = createNumber("contentOrder", Integer.class);

    public final StringPath contentType = createString("contentType");

    public final StringPath imagePath = createString("imagePath");

    public QBoardContent(String variable) {
        this(BoardContent.class, forVariable(variable), INITS);
    }

    public QBoardContent(Path<? extends BoardContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardContent(PathMetadata metadata, PathInits inits) {
        this(BoardContent.class, metadata, inits);
    }

    public QBoardContent(Class<? extends BoardContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

