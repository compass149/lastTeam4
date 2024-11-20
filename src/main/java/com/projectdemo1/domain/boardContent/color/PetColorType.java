package com.projectdemo1.domain.boardContent.color;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PetColorType {

    검은색,
    흰색,
    회색,
    크림색,
    초콜릿색,
    황금색,
    혼합,
    기타;

    @JsonCreator
    public static PetColorType fromString(String value) {
        try {
            return PetColorType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PetColorType.기타; // 기본값으로 "기타" 반환
        }
    }


    public String getColor() {
        return name();
    }
}
