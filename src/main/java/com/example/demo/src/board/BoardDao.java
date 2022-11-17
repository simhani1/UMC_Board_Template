package com.example.demo.src.board;

import com.example.demo.src.board.model.Board;
import com.example.demo.src.board.model.GetAllPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BoardDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 글 작성
     * POST
     */
    public int createPost(int userId, Board board) {
        String createPostQuery = "insert into Board (boardTypeId, userId, title, contents) values (?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{board.getBoardType(), userId, board.getTitle(), board.getContents()};
        this.jdbcTemplate.update(createPostQuery, createPostParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    /**
     * 전체 글 조회
     * GET
     */
    public List<GetAllPostRes> getAllPost() {
        String getAllPostQuery = "select BT.type         as boardType,\n" +
                "       U.nickname      as nickname,\n" +
                "       title,\n" +
                "       contents,\n" +
                "       Board.createdAt as time\n" +
                "from Board\n" +
                "         left join BoardType BT on Board.boardTypeId = BT.boardTypeId\n" +
                "         left join User U on Board.userId = U.userId\n" +
                "order by time;";
        return this.jdbcTemplate.query(getAllPostQuery,
                (rs, rowNum) -> new GetAllPostRes(
                        rs.getString("boardType"),
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("time")
                ));
    }

    /**
     * 특정 게시판 글 조회
     * GET
     */
    public List<GetAllPostRes> getAllPostResByType(int boardTypeId) {
        String getAllPostResByTypeQuery = "select BT.type         as boardType,\n" +
                "       U.nickname      as nickname,\n" +
                "       title,\n" +
                "       contents,\n" +
                "       Board.createdAt as time\n" +
                "from Board\n" +
                "         left join BoardType BT on Board.boardTypeId = BT.boardTypeId\n" +
                "         left join User U on Board.userId = U.userId\n" +
                "where Board.boardTypeId = ?\n" +
                "order by time;";
        int getAllPostResByTypeParams = boardTypeId;
        return this.jdbcTemplate.query(getAllPostResByTypeQuery,
                (rs, rowNum) -> new GetAllPostRes(
                        rs.getString("boardType"),
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("time")
                ),
                getAllPostResByTypeParams);
    }

    /*
     * 제목으로 글 검색
     * GET
     * */
    public List<GetAllPostRes> getAllPostResByKeyword(String keyword) {
        String getAllPostByKeywordQuery = "select BT.type         as boardType,\n" +
                "       U.nickname      as nickname,\n" +
                "       title,\n" +
                "       contents,\n" +
                "       Board.createdAt as time\n" +
                "from Board\n" +
                "         left join BoardType BT on Board.boardTypeId = BT.boardTypeId\n" +
                "         left join User U on Board.userId = U.userId\n" +
                "where title regexp ?\n" +
                "order by time;";
        String getAllPostByKeywordParams = keyword;
        return this.jdbcTemplate.query(getAllPostByKeywordQuery,
                (rs, rowNum) -> new GetAllPostRes(
                        rs.getString("boardType"),
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("time")
                ),
                getAllPostByKeywordParams);
    }

    /*
     * 작성자로 글 검색
     * GET
     * */
    public List<GetAllPostRes> getAllPostResByNickname(String nickname) {
        String getAllPostByNicknameQuery = "select BT.type         as boardType,\n" +
                "       U.nickname      as nickname,\n" +
                "       title,\n" +
                "       contents,\n" +
                "       Board.createdAt as time\n" +
                "from Board\n" +
                "         left join BoardType BT on Board.boardTypeId = BT.boardTypeId\n" +
                "         left join User U on Board.userId = U.userId\n" +
                "where nickname regexp ?\n" +
                "order by time;";
        String getAllPostByNicknameParams = nickname;
        return this.jdbcTemplate.query(getAllPostByNicknameQuery,
                (rs, rowNum) -> new GetAllPostRes(
                        rs.getString("boardType"),
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("time")
                ),
                getAllPostByNicknameParams);
    }
}
