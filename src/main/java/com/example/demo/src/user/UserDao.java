package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    /**
     * 회원가입
     * POST
     */
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (nickname, id, pwd) VALUES (?, ?, ?)";
        Object[] createUserParams = new Object[]{postUserReq.getNickname(), postUserReq.getId(), postUserReq.getPwd()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);// 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

//    // 회원정보 변경
//    public int modifyUserName(PatchUserReq patchUserReq) {
//        String modifyUserNameQuery = "update User set nickname = ? where userIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickname(), patchUserReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순
//
//        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
//    }

    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery="select pwd from User where id = ? and pwd = ?";
        Object[] getPwdParams = new Object[]{postLoginReq.getId(), postLoginReq.getPwd()};
        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getString("pwd")
                ),
                getPwdParams);
    }

    /**
     * 로그인
     * POST
     */
    public PostLoginRes getNickname(PostLoginReq postLoginReq) {
        String getNicknameQuery="select nickname from User where id = ? and pwd = ?";
        Object[] getNicknameParams = new Object[]{postLoginReq.getId(), postLoginReq.getPwd()};
        return this.jdbcTemplate.queryForObject(getNicknameQuery,
                (rs, rowNum) -> new PostLoginRes(
                        rs.getString("nickname")
                ),
                getNicknameParams);
    }

    /**
     * 탈퇴여부 확인
     * POST
     * */

    // 탈퇴한 유저인지 확인
    public PostCheckStatus checkStatus(String userId) {
        String checkStatusQuery = "select status\n" +
                "from umc_board.User\n" +
                "where id = ?";
        String checkStatusParams = userId;
        return jdbcTemplate.queryForObject(checkStatusQuery,
                (rs, rowNum) -> new PostCheckStatus(
                        rs.getBoolean("status")
                ),
                checkStatusParams);
    }
}
