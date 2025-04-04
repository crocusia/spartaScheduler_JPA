package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.CommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentResponseDto saveComment(Long userId, Long taskId, CommentRequestDto requestDto);

    Page<CommentResponseDto> getCommentsByTaskId(Long taskId, Pageable pageable);

    Page<CommentResponseDto> getCommentsByUserId(Long userId, Pageable pageable);

    CommentResponseDto findCommentById(Long id);

    CommentResponseDto updateComment(Long userId, Long id, CommentRequestDto requestDto);

    void deleteComment(Long userId, Long id);

}
