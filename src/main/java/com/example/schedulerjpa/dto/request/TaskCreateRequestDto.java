package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskCreateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title; //제목

    @NotBlank(message = "할일은 필수입니다.")
    @Size(max = 200, message = "할일은 최대 200자까지 입력 가능합니다.")
    private String content;     //할일
}
