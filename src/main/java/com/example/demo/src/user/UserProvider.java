package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserProvider {
    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    /**
     * 로그인
     * POST
     */
    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPwd(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPwd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if (postLoginReq.getPwd().equals(password)) { //비말번호가 일치한다면 userIdx를 가져온다.
            int userIdx = user.getUserId();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    /**
     * 탈퇴여부 검사
     * POST
     * */

    // 탈퇴한 유저인지 확인
    public PostCheckStatus checkStatus(PostLoginReq postLoginReq) throws BaseException {
        String userId = postLoginReq.getId();
        PostCheckStatus postCheckStatus = userDao.checkStatus(userId);
        return postCheckStatus;
    }
//
//    // 해당 이메일이 이미 User Table에 존재하는지 확인
//    public int checkEmail(String email) throws BaseException {
//        try {
//            return userDao.checkEmail(email);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
