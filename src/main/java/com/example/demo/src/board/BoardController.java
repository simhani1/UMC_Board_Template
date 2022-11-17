package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.Board;
import com.example.demo.src.board.model.GetAllPostRes;
import com.example.demo.src.board.model.PostWritingRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/board")
public class BoardController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final BoardService boardService;

    public BoardController(BoardProvider boardProvider, BoardService boardService) {
        this.boardProvider = boardProvider;
        this.boardService = boardService;
    }

    /**
     * 글 작성
     * POST
     */
    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<PostWritingRes> createPost(@PathVariable("userId") int userId, @RequestBody Board board) {
        try {
            PostWritingRes postWritingRes = boardService.createPost(userId, board);
            return new BaseResponse<>(postWritingRes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 전체 글 조회
     * GET
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAllPostRes>> getAllPost() {
        try {
            List<GetAllPostRes> getAllPostRes = boardProvider.getAllPost();
            return new BaseResponse(getAllPostRes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 특정 게시판 글 조회
     * GET
     */
    @ResponseBody
    @GetMapping("/{boardTypeId}")
    public BaseResponse<List<GetAllPostRes>> getAllPostResByType(@PathVariable("boardTypeId") int boardTypeId) {
        try {
            List<GetAllPostRes> getAllPostRes = boardProvider.getAllPostResByType(boardTypeId);
            return new BaseResponse<>(getAllPostRes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 제목으로 글 검색
     * GET
     * */
    @ResponseBody
    @GetMapping("/title")
    public BaseResponse<List<GetAllPostRes>> getAllPostResByKeyword(@RequestParam(required = true) String keyword) {
        try {
            List<GetAllPostRes> getAllPostResByKeyword = boardProvider.getAllPostResByKeyword(keyword);
            return new BaseResponse<>(getAllPostResByKeyword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 작성자로 글 검색
     * GET
     * */
    @ResponseBody
    @GetMapping("/writer")
    public BaseResponse<List<GetAllPostRes>> getAllPostResByWriter(@RequestParam(required = true) String nickname) {
        try {
            List<GetAllPostRes> getAllPostResByNickname = boardProvider.getAllPostResByNickname(nickname);
            return new BaseResponse<>(getAllPostResByNickname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}