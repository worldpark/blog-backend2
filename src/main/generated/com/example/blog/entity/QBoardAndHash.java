package com.example.blog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardAndHash is a Querydsl query type for BoardAndHash
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardAndHash extends EntityPathBase<BoardAndHash> {

    private static final long serialVersionUID = 1175028879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardAndHash boardAndHash = new QBoardAndHash("boardAndHash");

    public final QBoard board;

    public final NumberPath<Long> boardHashId = createNumber("boardHashId", Long.class);

    public final QHashTag hashTag;

    public QBoardAndHash(String variable) {
        this(BoardAndHash.class, forVariable(variable), INITS);
    }

    public QBoardAndHash(Path<? extends BoardAndHash> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardAndHash(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardAndHash(PathMetadata metadata, PathInits inits) {
        this(BoardAndHash.class, metadata, inits);
    }

    public QBoardAndHash(Class<? extends BoardAndHash> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
        this.hashTag = inits.isInitialized("hashTag") ? new QHashTag(forProperty("hashTag")) : null;
    }

}

