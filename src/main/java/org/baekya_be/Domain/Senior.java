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
    private Integer career;
    private String filter; // enum [ "IT / 인터넷" ... ]

    public Senior() {}

    public Senior(String user_id, String name, String job, String company, List<String> stack, Integer career, String filter) {
        this.user_id = user_id;
        this.name = name;
        this.job = job;
        this.company = company;
        this.stack = stack;
        this.career = career;
        this.filter = filter;
    }
}
