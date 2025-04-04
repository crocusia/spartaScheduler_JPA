package com.example.schedulerjpa.dto.response;

import com.example.schedulerjpa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
//세션 저장을 위한 직렬화
public class UserSessionDto implements Serializable {
    private Long userId;
    private String name;

    public UserSessionDto(User user){
        this.userId = user.getId();
        this.name = user.getName();
    }
}