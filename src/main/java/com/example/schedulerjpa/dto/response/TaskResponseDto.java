package com.example.schedulerjpa.dto.response;


import com.example.schedulerjpa.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;
    private Long userId;
    private String name;      //유저 이름
    private String title;     //제목
    private String content;   //내용
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;
    //댓글 개수
    //댓글들
    public TaskResponseDto(Task task){
        this.taskId = task.getId();
        this.userId = task.getUser().getId();
        this.name = task.getUser().getName();
        this.title = task.getTitle();
        this.content = task.getContent();
        this.updatedAt = task.getUpdatedAt();
    }
}
