package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllPostRes {
    private String boardType;
    private String nickname;
    private String title;
    private String contents;
    private String time;
}
