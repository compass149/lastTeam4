package com.projectdemo1.board4.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "board")
@Entity
@Table(name = "community_reply")
public class Creply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "cno")
    private Cboard cboard;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Creply parent; //부모 댓글

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Creply> children = new ArrayList<>(); //자식 댓글
}
