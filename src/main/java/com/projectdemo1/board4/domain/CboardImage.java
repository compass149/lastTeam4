package com.projectdemo1.board4.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CboardImage implements Comparable<CboardImage> {
    @Id
    private String uuid;
    private String fileName;
    private int ord;

    @ManyToOne
    private Cboard cboard;

    @Override
    public int compareTo(CboardImage other) {
        return this.ord - other.ord;
    }
    public void changeBoard(Cboard cboard) {
        this.cboard = cboard;
    }
}
