package com.projectdemo1.dto;

public class PetDTO {
    private String boardType; // 동물찾기 또는 주인찾기
    private String animalName; // 동물 이름
    private String regDate; // 등록 날짜
    private String gender; // 성별
    private String foundLocation; // 발견 장소
    private String characteristics; // 특징
    private String noticeNumber; // 공고 번호 (주인찾기일 경우)
    private String imageFileName; // 이미지 파일 이름
    private int visitCount; // 조회수
}
