package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.UserSessionDto;
import com.example.schedulerjpa.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 생성
    @PostMapping("tasks/{task_id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable("task_id") Long taskId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute("loginUser")UserSessionDto loginUser
    ){
        CommentResponseDto commentResponseDto = commentService.saveComment(loginUser.getUserId(), taskId, requestDto);
        return ResponseEntity.ok(commentResponseDto);
    }

    //일정에 따른 댓글 전체 조회
    @GetMapping("tasks/{task_id}/comments")
    public ResponseEntity<Page<CommentResponseDto>> findAllByTaskId(
            @PathVariable("task_id") Long taskId,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CommentResponseDto> commentResponseDtos = commentService.getCommentsByTaskId(taskId, pageable);
        return ResponseEntity.ok(commentResponseDtos);
    }

    //특정 유저 조회 시, 해당 유저가 작성한 모든 댓글 조회
    @GetMapping("users/{user_id}/comments")
    public ResponseEntity<Page<CommentResponseDto>> findAllByUserId(
            @PathVariable("user_id") Long userId,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CommentResponseDto> commentResponseDtos = commentService.getCommentsByUserId(userId, pageable);
        return ResponseEntity.ok(commentResponseDtos);
    }

    //단일 댓글 조회
    @GetMapping("comments/{id}")
    public ResponseEntity<CommentResponseDto> findCommentsById(
            @PathVariable Long id
    ){
        CommentResponseDto commentResponseDto = commentService.findCommentById(id);
        return ResponseEntity.ok(commentResponseDto);
    }

    //댓글 수정
    @PatchMapping("comments/{id}")
    public ResponseEntity<CommentResponseDto>updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute("loginUser") UserSessionDto loginUser
    ){
        CommentResponseDto commentResponseDto = commentService.updateComment(loginUser.getUserId(), id, requestDto);
        return ResponseEntity.ok(commentResponseDto);
    }

    //댓글 삭제
    @DeleteMapping("comments/{id}")
    public ResponseEntity<String> deleteComment(
        @PathVariable Long id,
        @SessionAttribute("loginUser") UserSessionDto loginUser
    ){
        commentService.deleteComment(loginUser.getUserId(), id);
        return  ResponseEntity.ok("댓글 삭제 성공");
    }
}

