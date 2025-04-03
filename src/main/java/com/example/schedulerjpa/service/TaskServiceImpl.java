package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.TaskCreateRequestDto;
import com.example.schedulerjpa.dto.request.TaskUpdateRequestDto;
import com.example.schedulerjpa.dto.response.TaskResponseDto;
import com.example.schedulerjpa.entity.Task;
import com.example.schedulerjpa.entity.User;
import com.example.schedulerjpa.repository.TaskRepository;
import com.example.schedulerjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    //일정 생성
    @Override
    public TaskResponseDto saveTask(Long userId, TaskCreateRequestDto createDto) {
        //연관관계를 위한 User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다."));
        //흠... 제목... 뭐 중복일 수 있지 굳이 확인하지 말고 그냥 만들자
        Task task = new Task(user, createDto.getTitle(), createDto.getContent());
        taskRepository.save(task);
        return new TaskResponseDto(task);
    }

    //일정 목록 조건 기반 조회
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt) {
        //문자열을 LocalDateTime으로 변환, null이면 그대로 null
        LocalDateTime dateTime = (updatedAt != null && !updatedAt.isEmpty())
                ? LocalDate.parse(updatedAt).atStartOfDay()
                : null;

        List<Task> tasks = taskRepository.findTasks(userId, dateTime);

        return tasks.stream()
                .map(TaskResponseDto::new)
                .collect(Collectors.toList());
    }

    //선택 일정 조회
    @Override
    public TaskResponseDto findTaskById(Long taskId) {
        Task task = taskRepository.findByid(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        return new TaskResponseDto(task);
    }

    //일정 수정
    @Override
    public TaskResponseDto updateTask(Long taskId, Long userId, TaskUpdateRequestDto updateDto) {
        Task task = taskRepository.findByid(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        //본인 일정이 맞는지 확인
        User user = task.getUser();
        if (!user.checkisMine(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "타인의 일정은 수정할 수 없습니다.");
        }

        if (updateDto.getTitle() != null) {
            task.updateTitle(updateDto.getTitle());
        }

        if (updateDto.getContent() != null) {
            task.updateContent(updateDto.getContent());
        }

        return new TaskResponseDto(task);
    }

    //일정 삭제
    @Override
    public void deleteTask(Long taskId, Long userId) {
        //본인 일정이 맞는지 확인
        Task task = taskRepository.findByid(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        User user = task.getUser();
        if (!user.checkisMine(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "타인의 일정은 삭제할 수 없습니다.");
        }
        //본인 일정이 맞으면 편하게 삭제
        taskRepository.delete(task);
    }
}
