package com.projectdemo1.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment", indexes = {@Index(name = "idx_comment_board", columnList = "bno")})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString(exclude = "board")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;


    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern ="yyyy-MM-dd")
    private Date regdate;

    @LastModifiedDate
    @javax.persistence.Column(name ="modDate" )
    private LocalDateTime modDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", nullable = false)
    @JsonIgnore
    private Board board;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Comment parent;


    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default //윤요섭 쌤 참조
    private List<Comment> children = new ArrayList<>();

}