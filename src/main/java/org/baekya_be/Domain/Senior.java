package org.baekya_be.Domain;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.signature.qual.Identifier;

import java.util.List;

@Getter
@Setter
public class Senior {
    @Identifier
    private String user_id;
    private String name;
    private String job;
    private String company;
    private List<String> stack;
    private String experience;
    private int career;
    private String filter; // enum [ "IT / 인터// 넷" ... ]
}
