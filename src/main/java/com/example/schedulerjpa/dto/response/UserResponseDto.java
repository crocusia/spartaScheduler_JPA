package com.example.schedulerjpa.dto.response;

import com.example.schedulerjpa.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String name;      //유저 이름
    private String email;     //이메일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;
    private Long commentsCount; //댓글 개수

    public UserResponseDto(User user, Long count){
        this.name = user.getName();
        this.email = user.getEmail();
        this.updatedAt = user.getUpdatedAt();
        this. commentsCount = count;
    }
}
