package com.example.schedulerjpa.dto.response;

import com.example.schedulerjpa.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long taskId;
    private Long userId;
    private String name;      //유저 이름
    private String comment;   //댓글
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt; //수정일

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getId();
        this.taskId = comment.getTask().getId();
        this.userId = comment.getUser().getId();
        this.name = comment.getUser().getName();
        this.comment = comment.getComment();
        this.updatedAt = comment.getUpdatedAt();
    }
}
