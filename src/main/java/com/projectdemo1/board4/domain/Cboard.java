package com.projectdemo1.board4.domain;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.projectdemo1.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "imageSet")
public class Cboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private Long uno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updatedAt")
    private LocalDateTime updatedAt;



    @ColumnDefault("0")
    private Long hitCount;
    @ColumnDefault("0")
    private Long replyCount;



    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ColumnDefault("0")
    private int visitCount;

    public void updateVisitcount() {
        this.visitCount = this.visitCount++;
    }

    //reply
    @OneToMany(mappedBy = "cboard", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cboard")
    private List<Creply> creplies;

    //image
    @OneToMany(mappedBy = "cboard", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<CboardImage> imageSet = new HashSet<>();

    //image
    public void addImage(String uuid, String fileName) {
        CboardImage image = CboardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .cboard(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(image);
    }
    public void clearImages() {
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }



}
