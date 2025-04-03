package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.TaskCreateRequestDto;
import com.example.schedulerjpa.dto.request.TaskUpdateRequestDto;
import com.example.schedulerjpa.dto.response.TaskResponseDto;
import com.example.schedulerjpa.dto.response.UserSessionDto;
import com.example.schedulerjpa.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor //의존성 자동 주입
public class TaskController {
    private final TaskService taskService;
    //일정 생성 - 유저는 본인 일정만 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskCreateRequestDto createDto,
            @SessionAttribute("loginUser") UserSessionDto loginUser
    ) {
        //세션에 저장된 데이터를 사용하기 위해 매개변수로 넘김
        TaskResponseDto taskResponseDto = taskService.saveTask(loginUser.getUserId(), createDto);
        return ResponseEntity.ok(taskResponseDto);
    }

    //특정 조건을 만족하는 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findTasks(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "") String updatedAt) {
        List<TaskResponseDto> taskResponseDto = taskService.findTasks(userId, updatedAt);
        return ResponseEntity.ok(taskResponseDto);
    }

    //단일 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.findTaskById(id);
        return ResponseEntity.ok(taskResponseDto);
    }

    //일정 수정 - 본인 일정만 가능
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequestDto updateDto,
            @SessionAttribute("loginUser") UserSessionDto loginUser
    ) {
        TaskResponseDto taskResponseDto = taskService.updateTask(id, loginUser.getUserId(), updateDto);
        return ResponseEntity.ok(taskResponseDto);
    }

    //일정 삭제 - 본일 일정만 가능
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id,
            @SessionAttribute("loginUser") UserSessionDto loginUser) {
        //본인 일정 지우는데 편하게 지우게하자 (비밀번호 추가 검증은 유저 삭제에서만!)
        //필터에서 이미 세션 검증한 상태
        taskService.deleteTask(id, loginUser.getUserId());
        return ResponseEntity.ok("일정 삭제 성공");
    }

}
