package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {
    @NotBlank(message = "현재 비밀번호는 필수 입력 값입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력 값입니다.")
    private String newPassword;
}