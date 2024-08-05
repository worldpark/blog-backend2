package com.example.blog.querydsl;

import com.example.blog.dto.HashTagDto;
import com.example.blog.entity.QBoardAndHash;
import com.example.blog.entity.QHashTag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashTagRepositotyDSLImpl implements HashTagRepositoryDsl{

    private final JPAQueryFactory jpaQueryFactory;

    private QHashTag qHashTag = QHashTag.hashTag;
    private QBoardAndHash qBoardAndHash = QBoardAndHash.boardAndHash;


    @Override
    public List<HashTagDto> getHashTagList() {

        List<HashTagDto> result = jpaQueryFactory
                .select(Projections.constructor(HashTagDto.class, qHashTag.hash_id.as("hashId"), qHashTag.hashName.as("hashLabel"), qBoardAndHash.count().as("boardCount")))
                .from(qHashTag)
                .leftJoin(qBoardAndHash)
                .on(qHashTag.hash_id.eq(qBoardAndHash.hashTag.hash_id))
                .groupBy(qHashTag.hash_id, qHashTag.hashName)
                .fetch();


        return result;
    }
}
