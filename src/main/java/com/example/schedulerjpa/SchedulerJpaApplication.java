package com.example.schedulerjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Audition 기능을 활성화
@SpringBootApplication
public class SchedulerJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerJpaApplication.class, args);
    }

}
