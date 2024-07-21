package com.example.blog.service;

import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    private final Logger logger = LoggerFactory.getLogger(UploadFileService.class);

    public void uploadFile(MultipartFile file, String uploadPath, String saltFileName) throws IOException {

        if(file.isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_DATA, "업로드 할 파일이 존재하지 않습니다.");
        }

        try{
            byte[] bytes = file.getBytes();
            Path filePath = Paths.get(uploadPath + saltFileName + "_" + file.getOriginalFilename());
            Files.write(filePath, bytes);

        }catch (NoSuchFileException noSuchFileException){
            logger.error("error : " + noSuchFileException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.NOT_EXISTS_PATH, "저장경로가 존재하지 않습니다.");

        }catch (Exception exception){
            logger.error("error : " + exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "서버 에러가 발생하였습니다.");
        }

    }
}
