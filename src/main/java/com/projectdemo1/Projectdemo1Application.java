package com.projectdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.projectdemo1.domain", "com.projectdemo1.board4.domain"}) //gpt가 추천해준 방법. 이상하면 지우기
public class Projectdemo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Projectdemo1Application.class, args);
    }
}
/*원래 소스코드
@SpringBootApplication
public class Projectdemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Projectdemo1Application.class, args);
    }

}*/