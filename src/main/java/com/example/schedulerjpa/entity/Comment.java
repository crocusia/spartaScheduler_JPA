package com.example.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //쓰기 지연 무시, persist() 시점에 바로 조회 가능 -> JPA가 알아야 하니까
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;    //유저 아이디(외래키)

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;    //유저 아이디(외래키)

    @Column(name = "content", nullable = false)
    private String comment;   //댓글

    public Comment(User user, Task task, String comment){
        this.user = user;
        this.task = task;
        this.comment = comment;
    }

    public void updateComment(String comment){
        this.comment = comment;
    }

}
