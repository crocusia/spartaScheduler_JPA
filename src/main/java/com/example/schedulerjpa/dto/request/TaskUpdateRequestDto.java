package com.example.schedulerjpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskUpdateRequestDto {
    private String title;
    private String content;
}
