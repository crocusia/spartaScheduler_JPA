package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.TaskCreateRequestDto;
import com.example.schedulerjpa.dto.request.TaskUpdateRequestDto;
import com.example.schedulerjpa.dto.response.TaskResponseDto;
import com.example.schedulerjpa.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    //일정 생성
    TaskResponseDto saveTask(Long userId, TaskCreateRequestDto createDto);
    //일정 목록 조건 기반 조회
    Page<TaskResponseDto> findTasks(Long userId, String updatedAt, Pageable pageable);
    //선택 일정 조회
    TaskResponseDto findTaskById(Long taskId);
    //일정 수정
    TaskResponseDto updateTask(Long taskId, Long userId, TaskUpdateRequestDto updateDto);
    //일정 삭제
    void deleteTask(Long taskId, Long userId);
}
