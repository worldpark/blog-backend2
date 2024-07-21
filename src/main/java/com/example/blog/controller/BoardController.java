package com.example.blog.controller;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardDto;
import com.example.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/getBoardList")
    public List<BoardDto> getBoardList(@RequestParam String hashTagName, @RequestParam Long pageNumber){

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
    public Long updateBoard(@RequestBody HashMap<String, Object> param){
        boardService.updateBoard(param);

        return 1L;
    }

    @DeleteMapping("/boardDelete")
    public void boardDelete(@RequestParam Long boardId){
        boardService.boardDelete(boardId);
    }
}
