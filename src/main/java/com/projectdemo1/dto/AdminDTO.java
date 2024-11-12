package com.projectdemo1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import com.projectdemo1.domain.User;
import com.projectdemo1.domain.boardContent.BoardImage;
import com.projectdemo1.domain.boardContent.Status;
import com.projectdemo1.domain.boardContent.color.PetColor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDTO {

    private Long uno;
    private String username;
    private String content;
    private String nickname;
    private String password;
    private User user;
    private String mobile;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public AdminDTO(Board board) {

        this.content = board.getContent();
        this.user = board.getUser();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.uno = user.getUno();
        this.mobile = user.getMobile();
        this.email = user.getEmail();
        this.createdAt = board.getCreatedAt();
    }
}