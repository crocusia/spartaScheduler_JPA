package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 200, message = "댓글은 최대 100자까지 입력 가능합니다.")
    private String comment;
}
