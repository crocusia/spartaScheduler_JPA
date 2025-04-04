package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserNameUpdateRequestDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
}
