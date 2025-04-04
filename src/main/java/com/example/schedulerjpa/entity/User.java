package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.config.PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //동명이인 가능

    @Column(nullable = false, unique = true)
    private String email; //중복 불가

    @Column(nullable = false)
    private String password;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean checkisMine(Long userId){
        return Objects.equals(this.id, userId);
    }
    public boolean checkPassword(String password, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(password, this.password);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
