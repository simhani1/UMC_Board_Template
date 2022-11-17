package com.example.demo.src.board;

import com.example.demo.src.board.model.GetAllPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardProvider {
    private final BoardDao boardDao;

    @Autowired
    public BoardProvider(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    /**
     * 전체 글 조회
     * GET
     */
    public List<GetAllPostRes> getAllPost() {
        List<GetAllPostRes> getAllPostRes = boardDao.getAllPost();
        return getAllPostRes;
    }

    /**
     * 특정 게시판 글 조회
     * GET
     */
    public List<GetAllPostRes> getAllPostResByType(int boardTypeId) {
        List<GetAllPostRes> getAllPostResByType = boardDao.getAllPostResByType(boardTypeId);
        return getAllPostResByType;
    }

    /*
     * 제목으로 글 검색
     * GET
     * */
    public List<GetAllPostRes> getAllPostResByKeyword(String keyword) {
        List<GetAllPostRes> getAllPostByKeyword = boardDao.getAllPostResByKeyword(keyword);
        return getAllPostByKeyword;
    }

    /*
     * 작성자로 글 검색
     * GET
     * */
    public List<GetAllPostRes> getAllPostResByNickname(String nickname) {
        List<GetAllPostRes> getAllPostResByNickname = boardDao.getAllPostResByNickname(nickname);
        return  getAllPostResByNickname;
    }
}
