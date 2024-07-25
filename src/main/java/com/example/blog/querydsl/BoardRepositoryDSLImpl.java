package com.example.blog.querydsl;

import com.example.blog.dto.*;
import com.example.blog.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryDSLImpl implements BoardRepositoryDsl{

    private final JPAQueryFactory jpaQueryFactory;
    private QBoard board = QBoard.board;
    private QBoardAndHash boardAndHash = QBoardAndHash.boardAndHash;
    private QHashTag hashTag = QHashTag.hashTag;
    private QBoardContent boardContent = QBoardContent.boardContent;

    private QBoardTextContentSub boardTextContentSub = QBoardTextContentSub.boardTextContentSub;

    @Override
    public List<BoardListDto> getBoardList(String hashTagName, Long pageNumber) {

        List<BoardListDto> responseData = new ArrayList<>();
        Long pageSize = 5L;
        Long offsetDate = (pageNumber * pageSize);

        List<BoardTextContentSub> previewContent = jpaQueryFactory
                .selectFrom(boardTextContentSub)
                .fetch();

        List<Board> result = jpaQueryFactory
                .select(board)
                .from(board)
                .leftJoin(boardAndHash)
                .on(board.boardId.eq(boardAndHash.board.boardId)).fetchJoin()
                .leftJoin(hashTag)
                .on(boardAndHash.hashTag.hash_id.eq(hashTag.hash_id)).fetchJoin()
                .where(hashTagEq(hashTagName))
                .offset(offsetDate)
                .limit(pageSize)
                .orderBy(board.boardId.desc())
                .fetch();

        for (Board resultBoard: result) {

            BoardListDto responseDto = new BoardListDto(
                    resultBoard.getBoardId()
                    , resultBoard.getBoardTitle()
            );

            HashTag hashData = new HashTag();
            List<HashTag> hashDataList = new ArrayList<>();

            for(BoardAndHash boardAndHashData: resultBoard.getBoardAndHash()){
                hashData.setHash_id(boardAndHashData.getHashTag().getHash_id());
                hashData.setHashName(boardAndHashData.getHashTag().getHashName());

                hashDataList.add(hashData);
            }

            responseDto.setHashTags(hashDataList);

            for (BoardTextContentSub resultBoardText : previewContent){

                if(resultBoard.getBoardId() == resultBoardText.getBoardId()){
                    responseDto.setPreview_content(resultBoardText.getPreviewContent());
                    previewContent.remove(resultBoardText);
                    break;
                }

            }

            responseData.add(responseDto);
        }

        return responseData;
    }

    @Override
    public BoardContentDto getBoardContent(Long boardId) {

        List<Board> boardResult = jpaQueryFactory
                .selectFrom(board)
                .leftJoin(board.boardAndHash, boardAndHash).fetchJoin()
                .leftJoin(boardAndHash.hashTag, hashTag)
                .where(board.boardId.eq(boardId))
                .fetch();

        List<BoardContent> boardContents = jpaQueryFactory
                .selectFrom(boardContent)
                .rightJoin(boardContent.board, board)
                .where(board.boardId.eq(boardId))
                .orderBy(boardContent.contentOrder.asc())
                .fetch();

        Board board = boardResult.get(0);
        BoardContentDto responseData = new BoardContentDto(board.getBoardId(), board.getBoardTitle(), board.getCreateTime());

        List<ContentsDto> contentList = new ArrayList<>();
        List<BoardHashTagDto> hashTagList = new ArrayList<>();

        for(BoardAndHash hashData : board.getBoardAndHash()){
            BoardHashTagDto hashTagData = new BoardHashTagDto(hashData.getBoardHashId()
                    , hashData.getHashTag().getHashName());

            hashTagList.add(hashTagData);
        }

        for(BoardContent contentData : boardContents){
            ContentsDto contentsDto = new ContentsDto(contentData.getContentType()
                    , contentData.getBoardContentText()
                    , contentData.getImagePath());

            contentList.add(contentsDto);
        }

        responseData.setHashTags(hashTagList);
        responseData.setBoard_content_list(contentList);

        return responseData;
    }

    private BooleanExpression hashTagEq(String hashTagName){
        if(hashTagName == null || hashTagName == ""){
            return null;
        }

        return this.hashTag.hashName.eq(hashTagName);
    }
}
