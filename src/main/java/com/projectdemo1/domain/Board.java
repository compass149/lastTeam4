package com.projectdemo1.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity(name = "tbl_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String title;
    private String writer;
    private String content;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="regdate")
    private Date regDate;
    @ColumnDefault("0") //기본값 0
    private Long hitcount; //조회수
    //private Long replycnt; //댓글 수 추가하려면

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateHitcount() {
        this.hitcount =  this.hitcount+1; //조회수 증가
    }

}
