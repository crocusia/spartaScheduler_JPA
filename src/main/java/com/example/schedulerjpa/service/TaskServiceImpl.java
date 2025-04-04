package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.TaskCreateRequestDto;
import com.example.schedulerjpa.dto.request.TaskUpdateRequestDto;
import com.example.schedulerjpa.dto.response.TaskResponseDto;
import com.example.schedulerjpa.entity.Task;
import com.example.schedulerjpa.entity.User;
import com.example.schedulerjpa.repository.CommentRepository;
import com.example.schedulerjpa.repository.TaskRepository;
import com.example.schedulerjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    //일정 생성
    @Override
    public TaskResponseDto saveTask(Long userId, TaskCreateRequestDto createDto) {
        //연관관계를 위한 User 조회
        User user = userRepository.findByIdOrElseThrow(userId);
        //흠... 제목... 뭐 중복일 수 있지 굳이 확인하지 말고 그냥 만들자
        Task task = new Task(user, createDto.getTitle(), createDto.getContent());
        taskRepository.save(task);
        return new TaskResponseDto(task, commentRepository.countByTaskId(task.getId()));
    }

    //일정 목록 조건 기반 조회
    @Override
    public Page<TaskResponseDto> findTasks(Long userId, String updatedAt, Pageable pageable) {
        Specification<Task> spec = Specification.where(null); //아무 조건도 없는 상태

        //userId가 null이 아닐 때, Task의 user의 id와 userId를 비교하는 조건 추가
        if(userId != null){
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId)));
        }

        if(updatedAt != null && !updatedAt.isBlank()){
            //문자열을 LocalDate로 변환 (YYYY-mm-dd)
            //캘린더에서 선택하는 방식을 사용한다는 가정하에 DATE 형식이 맞지 않는다는 가정은 제외함
            //아닐 경우 형식 검사 추가 필요
            LocalDate date = LocalDate.parse(updatedAt, DateTimeFormatter.ISO_LOCAL_DATE);
            //문자열
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.function("DATE", LocalDate.class, root.get("updatedAt")), date));
        }
        Page<Task> taskPage = taskRepository.findAll(spec, pageable);

        //페이지 내의 일정 id를 모아옴
        List<Long> taskIds = taskPage.getContent().stream()
                .map(Task::getId)
                .toList();

        //id에 따른 개수를 매핑
        Map<Long, Long> commentCounts = commentRepository.countByTaskIds(taskIds).stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        //Task와 count로 TaskRespinseDto 리스트 생성
        return taskPage.map(task -> {
            Long count = commentCounts.getOrDefault(task.getId(), 0L);
            return new TaskResponseDto(task, count);
        });
    }

    //선택 일정 조회
    @Override
    public TaskResponseDto findTaskById(Long taskId) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        return new TaskResponseDto(task, commentRepository.countByTaskId(task.getId()));
    }

    //일정 수정
    @Override
    public TaskResponseDto updateTask(Long taskId, Long userId, TaskUpdateRequestDto updateDto) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
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
        taskRepository.save(task);
        return new TaskResponseDto(task, commentRepository.countByTaskId(task.getId()));
    }

    //일정 삭제
    @Override
    public void deleteTask(Long taskId, Long userId) {
        //본인 일정이 맞는지 확인
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        User user = task.getUser();
        if (!user.checkisMine(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "타인의 일정은 삭제할 수 없습니다.");
        }
        //본인 일정이 맞으면 편하게 삭제
        taskRepository.delete(task);
    }
}
