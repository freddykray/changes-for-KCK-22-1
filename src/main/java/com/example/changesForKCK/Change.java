package com.example.changesForKCK;

import lombok.Data;

@Data
public class Change {

    private String type;
    private Integer pair;
    private Integer fromPair;
    private Integer toPair;
    private String oldTeacher;
    private String newTeacher;

}
