package com.example.blog.controller;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardListDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.ResponseDto;
import com.example.blog.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/getBoardList")
    public List<BoardListDto> getBoardList(@RequestParam String hashTagName, @RequestParam Long pageNumber){

        return boardService.getBoardList(hashTagName, pageNumber);
    }

    @GetMapping("/getBoardContent")
    public BoardContentDto getBoardContent(@RequestParam Long boardId){
        return boardService.getBoardContent(boardId);
    }

    @PostMapping("/insertBoard")
    public Long insertBoard(@RequestBody HashMap<String, Object> param){

        Long boardId = boardService.insertBoard(param);

        return boardId;
    }

    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam(value = "boardImage", required = false) List<MultipartFile> file
            , @RequestParam("boardId") Long boardId){

        boardService.uploadBoardImage(file, boardId);
    }

    @PutMapping("/updateBoard")
    public void updateBoard(@RequestBody HashMap<String, Object> param){
        boardService.updateBoard(param);
    }

    @DeleteMapping("/boardDelete")
    public void boardDelete(@RequestParam Long boardId){
        boardService.boardDelete(boardId);
    }

    @GetMapping("/getComment")
    public List<CommentDto> getComment(@RequestParam Long boardId){
        return boardService.getComment(boardId);
    }

    @PostMapping("/insertComment")
    public void insertComment(@RequestParam HashMap<String, Object> param, Principal principal, HttpServletRequest request){
        //param.put("ip", request.getRemoteAddr());
        boardService.insertComment(param, principal);
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<ResponseDto> deleteComment(@RequestParam HashMap<String, Object> param){
        return boardService.deleteComment(param);
    }

    @PutMapping("/blindComment")
    public ResponseEntity<ResponseDto> blindComment(@RequestParam HashMap<String, String> param){
        return boardService.blindComment(param);
    }
}
