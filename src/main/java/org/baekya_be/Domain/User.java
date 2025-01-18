package org.baekya_be.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String user_id; // PK로 사용되는 고유 ID
    private int type; // 시니어(0)인지 청년(1)인지
    private String name; // 사용자 이름
    private String loginId; // 회원가입 및 로그인에 사용하는 아이디
    private String password; // 비밀번호
    private String job; // 직종 (12 중 택 1)

    public User() {
    }

    public User(String user_id, int type, String name, String loginId, String password, String job) {
        this.user_id = user_id;
        this.type = type;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.job = job;
    }
}