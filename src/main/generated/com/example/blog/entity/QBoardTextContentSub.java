package com.example.blog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardTextContentSub is a Querydsl query type for BoardTextContentSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardTextContentSub extends EntityPathBase<BoardTextContentSub> {

    private static final long serialVersionUID = 1784080330L;

    public static final QBoardTextContentSub boardTextContentSub = new QBoardTextContentSub("boardTextContentSub");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath previewContent = createString("previewContent");

    public QBoardTextContentSub(String variable) {
        super(BoardTextContentSub.class, forVariable(variable));
    }

    public QBoardTextContentSub(Path<? extends BoardTextContentSub> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardTextContentSub(PathMetadata metadata) {
        super(BoardTextContentSub.class, metadata);
    }

}

