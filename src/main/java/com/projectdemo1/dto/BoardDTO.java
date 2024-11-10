package com.projectdemo1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectdemo1.domain.Comment;
import com.projectdemo1.domain.boardContent.BoardImage;
import com.projectdemo1.domain.boardContent.PetType;
import com.projectdemo1.domain.boardContent.PostType;
import com.projectdemo1.domain.boardContent.Status;
import com.projectdemo1.domain.boardContent.color.PetColor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String writer;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore
    private LocalDateTime modDate;

    private List<String> fileNames;

    private PostType postType;

    private Long hitCount;

    private Set<BoardImage> imageSet;
    private List<Comment> comments;
    private Status status;
    private String petDescription;
    private Date lostDate;
    private String lostLocation;
    private String petBreeds;
    private String petGender;
    private String petAge;
    private String petWeight;
    @Enumerated(EnumType.STRING)
    private PetType petType; //동물 종류

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petColor")
    private PetColor petColor; //동물 색상

}
