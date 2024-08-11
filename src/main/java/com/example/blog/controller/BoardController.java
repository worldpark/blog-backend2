package com.example.blog.controller;

import com.example.blog.dto.BoardContentDto;
import com.example.blog.dto.BoardListDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.ResponseDto;
import com.example.blog.exception.ErrorDto;
import com.example.blog.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Board", description = "게시판과 관련된 처리")
@ApiResponse(responseCode = "400", description = "필요 데이터를 전달받지 못하였거나, NullException 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "500", description = "JPA 예외 혹은 기타 예외 발생", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
@ApiResponse(responseCode = "200", description = "데이터를 정상적으로 처리")
public class BoardController {

    private final BoardService boardService;


    @Operation(summary = "게시판 목록 데이터 가져옴", description = "사용자의 해시태그의 선택에 따라서 그에 해당하는 게시글 목록을 가져옴")
    @GetMapping("/getBoardList")
    public List<BoardListDto> getBoardList(@Parameter(description = "사용자가 선택한 해시태그 이름", required = false) @RequestParam(required = false) String hashTagName
            ,@Parameter(description = "목록 스크롤 단계에 따른 리스트 출력(무한스크롤 번호선택 0 부터 시작)") @RequestParam Long pageNumber){

        return boardService.getBoardList(hashTagName, pageNumber);
    }

    @Operation(summary = "게시글 내용 데이터 가져옴", description = "사용자의 게시글의 선택에 따라서 그에 해당하는 게시글 내용을 가져옴")
    @GetMapping("/getBoardContent")
    public BoardContentDto getBoardContent(@Parameter(description = "사용자가 선택한 게시글 ID") @RequestParam Long boardId){
        return boardService.getBoardContent(boardId);
    }

    @Operation(summary = "게시글 내용을 등록", description = "게시글 제목, 내용, 해시태그를 매개변수로 받아서 DB에 등록")
    @ApiResponse(responseCode = "200", description = "데이터를 정상적으로 처리 후 게시글 ID 출력")
    @PostMapping("/insertBoard")
    public Long insertBoard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "등록할 게시글 내용",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{\"board_title\": \"게시글 제목\"" +
                                    ", \"board_content_list\": \"게시글 내용\"" +
                                    ", \"hash_tags\": \"적용할 해시태그 리스트\"}"
                    )
                ))
            @RequestBody HashMap<String, Object> param){

        Long boardId = boardService.insertBoard(param);

        return boardId;
    }

    @Operation(summary = "게시글 내용의 이미지 저장", description = "게시글 이미지, 게시글 ID를 매개변수로 서버에 이미지 저장")
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam(value = "boardImage", required = false) List<MultipartFile> file
            , @Parameter(description = "이미지가 등록될 게시글의 ID") @RequestParam("boardId") Long boardId){

        boardService.uploadBoardImage(file, boardId);
    }

    @Operation(summary = "게시글 내용을 수정", description = "게시글 제목, 내용, 해시태그를 매개변수로 받아서 DB에 수정")
    @PutMapping("/updateBoard")
    public void updateBoard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 게시글 내용",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"board_id\": \"게시글 ID\"" +
                                            ", \"board_title\": \"게시글 제목\"" +
                                            ", \"board_content_list\": \"게시글 내용\"" +
                                            ", \"hash_tags\": \"적용할 해시태그 리스트\"}"
                            )
                    ))
            @RequestBody HashMap<String, Object> param){
        boardService.updateBoard(param);
    }

    @Operation(summary = "게시글을 삭제", description = "게시글 ID를 매개변수로 받아서 해당 게시글 삭제")
    @DeleteMapping("/boardDelete")
    public void boardDelete(@Parameter(description = "삭제될 게시글의 ID") @RequestParam Long boardId){
        boardService.boardDelete(boardId);
    }

    @Operation(summary = "댓글 출력", description = "게시글 ID를 매개변수로 받아서 해당 게시글의 댓글 출력")
    @GetMapping("/getComment")
    public List<CommentDto> getComment(@Parameter(description = "가져올 댓글의 게시글 ID") @RequestParam Long boardId){
        return boardService.getComment(boardId);
    }

    @Operation(summary = "댓글 등록", description = "댓글 내용을 매개변수로 받아서 댓글 등록")
    @PostMapping("/insertComment")
    public void insertComment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 댓글 내용",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"boardId\": \"게시글 ID\"" +
                                            ", \"parentId\": \"0\"" +
                                            ", \"commentContents\": \"댓글 내용\"" +
                                            ", \"commentPassword\": \"댓글 암호\"}"
                            )
                    ))
            @RequestBody HashMap<String, Object> param
            , Principal principal){
        //param.put("ip", request.getRemoteAddr());

        boardService.insertComment(param, principal);
    }

    @Operation(summary = "댓글 삭제", description = "댓글의 암호 및 ID를 매개변수로 받아서 암호가 맞는지 확인하여 삭제 진행")
    @DeleteMapping("/deleteComment")
    public ResponseEntity<ResponseDto> deleteComment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "댓글 삭제 처리",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"commentId\": \"게시글 ID\"" +
                                            ", \"commentPassword\": \"댓글 암호\"}"
                            )
                    ))
            @RequestBody HashMap<String, Object> param){
        return boardService.deleteComment(param);
    }

    @Operation(summary = "댓글 블라인드", description = "댓글의 ID를 매개변수로 받아서 댓글 상태 블라인드 처리")
    @PutMapping("/blindComment")
    public ResponseEntity<ResponseDto> blindComment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "블라인드 처리할 댓글 내용",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"commentId\": \"댓글 ID\"}"
                            )
                    ))
            @RequestBody HashMap<String, String> param){
        return boardService.blindComment(param);
    }
}
