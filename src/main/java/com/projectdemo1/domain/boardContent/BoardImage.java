package com.projectdemo1.domain.boardContent;



import com.projectdemo1.domain.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage implements Comparable<BoardImage> {
    @Id
    private String uuid;
    private String fileName;
    private int ord;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno") // board 테이블의 bno를 참조
    private Board board;



    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    } // 정렬을 위한 메소드

    public void changeBoard(Board board) {
        this.board = board;
    } // 게시글과 이미지를 연결하기 위한 메소드
}
