package org.baekya_be.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeniorAddDTO {
    private String user_id;
    private String name;
    private String job;
    private String company;
    private List<String> stack;
    private Integer career;
    private String filter;
}
