package com.projectdemo1.board4.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class CpageResponseDTO<E> {

    private int page;
    private int size;
    private int total;
    private int start;
    private int end;
    private boolean prev, next;
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public CpageResponseDTO(CpageRequestDTO cpageRequestDTO, int total, List<E> dtoList) {

        if(total <= 0) {
            return;
        }

        this.page = cpageRequestDTO.getPage();
        this.size = cpageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.end =(int)(Math.ceil((double) this.page/this.size))*this.size;
        this.start = this.end - (this.size - 1);
        int last = (int) (Math.ceil((total / (double)size)));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
