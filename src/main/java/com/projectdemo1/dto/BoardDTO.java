package com.projectdemo1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.Comment;
import com.projectdemo1.domain.User;
import com.projectdemo1.domain.boardContent.BoardImage;
import com.projectdemo1.domain.boardContent.PetType;
import com.projectdemo1.domain.boardContent.PostType;
import com.projectdemo1.domain.boardContent.Status;
import com.projectdemo1.domain.boardContent.color.PetColor;
import com.projectdemo1.domain.boardContent.color.PetColorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class BoardDTO {

    private Long bno;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String writer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime modDate;
    private LocalDateTime updatedAt;


    private List<String> fileNames;

    private PostType postType;

    private Long hitCount;

    private Set<BoardImage> imageSet;
    private List<Comment> comments;
    private Status status;
    private String petDescription;
    private Date lostDate;
    private String lostLocation;
    private String petBreeds; //동물 품종
    private String petGender;
    private String petAge;
    private String petWeight;
    @Enumerated(EnumType.STRING)
    private PetType petType; //동물 종류(개, 고양이 등)
    private String petName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petColor")
    private PetColorType petColor; //동물 색상
    private User user;
    private String mobile;
    private String email;
    private PetColorType color;


    public BoardDTO(Board board) {
        this.bno = board.getBno();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.petColor = board.getPetColor().getColor(); // PetColor 설정
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.user = board.getUser();
        this.hitCount = board.getHitCount();
        this.imageSet = board.getImageSet();
        this.comments = board.getComments();
        this.status = board.getStatus();
        this.petDescription = board.getPetDescription();
        this.lostDate = board.getLostDate();
        this.lostLocation = board.getLostLocation();
        this.petBreeds = board.getPetBreeds();
        this.petGender = board.getPetGender();
        this.petAge = board.getPetAge();
        this.petWeight = board.getPetWeight();
        this.petType = board.getPetType();
        this.petName = board.getPetName();
    }
}
