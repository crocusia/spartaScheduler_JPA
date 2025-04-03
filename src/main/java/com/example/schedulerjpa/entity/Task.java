package com.example.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //쓰기 지연 무시, persist() 시점에 바로 조회 가능 -> JPA가 알아야 하니까
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;    //유저 아이디(외래키)

    @Column(nullable = false)
    private String title;   //제목

    private String content; //내용

    public Task(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }
}