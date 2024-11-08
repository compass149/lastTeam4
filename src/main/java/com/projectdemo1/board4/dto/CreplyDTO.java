package com.projectdemo1.board4.dto;

import lombok.Data;

@Data
public class CreplyDTO {

    private String replyText;
    private String replyer;
    private Long parentId; //부모 댓글의 rno
}
