package com.example.schedulerjpa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserSignUpResponseDto {
    private String name;
    private String email;
}


