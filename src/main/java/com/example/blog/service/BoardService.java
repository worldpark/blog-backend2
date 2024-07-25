package com.example.blog.service;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardListDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.BoardAndHash;
import com.example.blog.entity.BoardContent;
import com.example.blog.entity.HashTag;
import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import com.example.blog.querydsl.BoardRepositoryDsl;
import com.example.blog.repository.BoardAndHashRepository;
import com.example.blog.repository.BoardContentRepository;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryDsl boardRepositoryDsl;

    private final HashTagRepository hashTagRepository;
    private final BoardAndHashRepository boardAndHashRepository;
    private final BoardContentRepository boardContentRepository;

    private final UploadFileService uploadFileService;

    private final Logger logger = LoggerFactory.getLogger(BoardService.class);

    public List<BoardListDto> getBoardList(String hashTagName, Long pageNumber) {

        try {
            List<BoardListDto> result = boardRepositoryDsl.getBoardList(hashTagName, pageNumber);
            return result;

        } catch (NullPointerException nullPointerException) {
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "검색할 해시태그가 없습니다.");

        }catch (JpaSystemException jpaSystemException){
            logger.error("param - hashTagName : " + hashTagName + " pageNumber : " + pageNumber);
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        } catch (Exception exception) {
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "게시글 목록을 가져오지 못했습니다.");
        }

    }

    public BoardContentDto getBoardContent(Long boardId) {

        try {
            BoardContentDto result = boardRepositoryDsl.getBoardContent(boardId);
            return result;

        } catch (NullPointerException nullPointerException) {
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "서버가 게시글 번호를 전달받지 못했습니다.");

        } catch (JpaSystemException jpaSystemException){
            logger.error("param : " + boardId);
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception) {
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "게시글 목록을 가져오지 못했습니다.");
        }
    }

    @Transactional
    public Long insertBoard(HashMap<String, Object> param) {

        try{
            Board board = new Board();
            board.setBoardTitle(param.get("board_title").toString());
            board.setCreateTime(LocalDateTime.now());

            Long returnData = boardRepository.save(board).getBoardId();

            for (LinkedHashMap<String, Object> contents : (List<LinkedHashMap<String, Object>>) param.get("board_content_list")) {

                BoardContent boardContent = new BoardContent();

                boardContent.setBoard(board);
                boardContent.setContentOrder(Integer.parseInt(contents.get("content_order").toString()));
                boardContent.setContentType(contents.get("content_type").toString());
                if (boardContent.getContentType().equals("text")) {
                    boardContent.setBoardContentText(contents.get("board_content").toString());

                } else if (boardContent.getContentType().equals("image")) {
                    boardContent.setImagePath(returnData + "_" + contents.get("image_path").toString());
                }

                boardContentRepository.save(boardContent);
            }

            for (String tag : (List<String>) param.get("hash_tags")) {

                HashTag hashTag = hashTagRepository.findByHashName(tag);

                if (hashTag == null) {
                    hashTag = new HashTag();
                    hashTag.setHashName(tag);

                    hashTagRepository.save(hashTag);
                }

                BoardAndHash boardAndHash = new BoardAndHash();
                boardAndHash.setBoard(board);
                boardAndHash.setHashTag(hashTag);

                boardAndHashRepository.save(boardAndHash);

            }

            return returnData;
        }catch (NullPointerException nullPointerException){
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "서버가 데이터를 전달받지 못했습니다.");

        }catch (JpaSystemException jpaSystemException){
            param.forEach((key, value)->{
                logger.error("param - key : " + key);
                logger.error("param - value : {}" + value);
            });
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }


    }

    public void uploadBoardImage(List<MultipartFile> files, Long boardId) {

        try {
            for (MultipartFile file : files) {
                String os = System.getProperty("os.name").toLowerCase();

                if(os.contains("win")){
                    uploadFileService.uploadFile(file, "C:\\blogImage\\", boardId.toString());
                }else{
                    uploadFileService.uploadFile(file, "/Users/kimjr/Documents/blogImage/", boardId.toString());
                }
            }

        }catch (NullPointerException nullPointerException){
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "서버가 데이터를 전달받지 못했습니다.");

        }catch (Exception exception) {
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");
        }
    }

    @Transactional
    public void updateBoard(HashMap<String, Object> param) {

        try{
            Board board = boardRepository.findById(Long.parseLong(param.get("board_id").toString()))
                    .orElseThrow(() -> new NullPointerException());

            board.setBoardTitle(param.get("board_title").toString());

            boardRepository.save(board);

            List<BoardContent> boardContents = boardContentRepository.findByBoardOrderByContentOrder(board);
            List<LinkedHashMap<String, Object>> contentsList = (List<LinkedHashMap<String, Object>>) param.get("board_content_list");

            int index = 0;
            for (BoardContent boardContent : boardContents) {

                if (index <= contentsList.size() - 1) {
                    LinkedHashMap<String, Object> contents = contentsList.get(index);

                    boardContent.setContentOrder(Integer.parseInt(contents.get("content_order").toString()));
                    boardContent.setContentType(contents.get("content_type").toString());

                    if (boardContent.getContentType().equals("text")) {
                        boardContent.setBoardContentText(contents.get("board_content").toString());

                    } else if (boardContent.getContentType().equals("image")) {
                        boardContent.setImagePath(board.getBoardId() + "_" + contents.get("image_path").toString());
                    }

                    boardContentRepository.save(boardContent);

                } else {
                    boardContentRepository.delete(boardContent);
                }

                index++;
            }

            for (int i = index; i < contentsList.size(); i++) {
                LinkedHashMap<String, Object> contents = contentsList.get(index);
                BoardContent boardContent = new BoardContent();

                boardContent.setBoard(board);

                boardContent.setContentOrder(Integer.parseInt(contents.get("content_order").toString()));
                boardContent.setContentType(contents.get("content_type").toString());

                if (boardContent.getContentType().equals("text")) {
                    boardContent.setBoardContentText(contents.get("board_content").toString());

                } else if (boardContent.getContentType().equals("image")) {
                    boardContent.setImagePath(board.getBoardId() + "_" + contents.get("image_path").toString());
                }

                boardContentRepository.save(boardContent);
            }

            boardAndHashRepository.deleteByBoard(board);

            for (String tag : (List<String>) param.get("hash_tags")) {

                HashTag hashTag = hashTagRepository.findByHashName(tag);

                if (hashTag == null) {
                    hashTag = new HashTag();
                    hashTag.setHashName(tag);

                    hashTagRepository.save(hashTag);
                }

                BoardAndHash boardAndHash = new BoardAndHash();
                boardAndHash.setBoard(board);
                boardAndHash.setHashTag(hashTag);

                boardAndHashRepository.save(boardAndHash);

            }
        }catch (NullPointerException nullPointerException){
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "서버가 데이터를 전달받지 못했습니다.");

        }catch (JpaSystemException jpaSystemException){
            param.forEach((key, value)->{
                logger.error("param - key : " + key);
                logger.error("param - value : {}" + value);
            });
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }

    }

    @Transactional
    public void boardDelete(Long boardId){

        try{
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new NullPointerException());

            boardAndHashRepository.deleteByBoard(board);
            boardContentRepository.deleteByBoard(board);
            boardRepository.deleteById(boardId);
        }catch (NullPointerException nullPointerException){
            logger.error("error : " + nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "서버가 데이터를 전달받지 못했습니다.");

        }catch (JpaSystemException jpaSystemException){
            logger.error("param : " + boardId);
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");
        }


    }
}
