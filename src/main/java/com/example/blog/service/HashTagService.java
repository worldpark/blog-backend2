package com.example.blog.service;

import com.example.blog.dto.HashTagDto;
import com.example.blog.dto.ResponseDto;
import com.example.blog.entity.Board;
import com.example.blog.entity.HashTag;
import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import com.example.blog.querydsl.BoardRepositoryDsl;
import com.example.blog.repository.BoardAndHashRepository;
import com.example.blog.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;
    private final BoardAndHashRepository boardAndHashRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public List<HashTagDto> getHashTagList(){

        try{
            List<HashTag> result = hashTagRepository.findAll();

            List<HashTagDto> dtos = result.stream()
                    .map(data -> new HashTagDto(data.getHash_id(), data.getHashName()))
                    .collect(Collectors.toList());

            return dtos;
        }catch (JpaSystemException jpaSystemException){
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "알 수 없는 에러가 발생하였습니다.");
        }
    }

    public void setHashTag(String hashLabel){

        try{
            if(hashLabel.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.FAIL_SAVE, "빈값은 해시태그로 저장 할 수 없습니다.");
            }

            HashTag hashTag = new HashTag();
            hashTag.setHashName(hashLabel);

            hashTagRepository.save(hashTag);
        }catch (NullPointerException nullPointerException){
            logger.error("error : ", nullPointerException);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.FAIL_SAVE, "저장하려는 해시태그 값이 존재하지 않습니다.");

        }catch (JpaSystemException jpaSystemException){
            logger.error("param : " + hashLabel);
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.FAIL_SAVE, "해시태그를 저장하지 못하였습니다.");
        }

    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteHashTag(Long hashId){

        try {
            if(hashId == null){
                throw new NullPointerException();
            }
            HashTag hashTag = hashTagRepository.findById(hashId)
                    .orElseThrow(() -> new NullPointerException());

            boardAndHashRepository.deleteByHashTag(hashTag);
            hashTagRepository.deleteById(hashId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .code("200")
                            .message("해시태그 삭제 성공")
                            .build());

        }catch (NullPointerException nullPointerException){
            logger.error("error : ", nullPointerException);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .code(CustomErrorCode.NOT_EXISTS_DATA.getCode())
                            .message(CustomErrorCode.NOT_EXISTS_DATA.getMessage())
                            .build());

        }catch (JpaSystemException jpaSystemException){
            logger.error("param : " + hashId);
            logger.error("JPA error : ", jpaSystemException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");

        }catch (Exception exception){
            logger.error("error : ", exception);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .code(CustomErrorCode.UNKNOWN_ERROR.getCode())
                            .message(CustomErrorCode.UNKNOWN_ERROR.getMessage())
                            .build());
        }
    }
}
