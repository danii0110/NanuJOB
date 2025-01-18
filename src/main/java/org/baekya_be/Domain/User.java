package org.baekya_be.Domain;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.signature.qual.Identifier;

@Getter
@Setter
public class User {
    @Identifier
    private String user_id;
    private String username;
    private int type; // 시니어(0)인지 청년(1)인지

    public User() {
    }

    public User(String user_id, String username, int type) {
        this.user_id = user_id;
        this.username = username;
        this.type = type;
    }


}
