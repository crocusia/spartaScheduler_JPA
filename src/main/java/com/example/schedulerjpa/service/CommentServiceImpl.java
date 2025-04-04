package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.CommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.entity.Comment;
import com.example.schedulerjpa.entity.Task;
import com.example.schedulerjpa.entity.User;
import com.example.schedulerjpa.repository.CommentRepository;
import com.example.schedulerjpa.repository.TaskRepository;
import com.example.schedulerjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto saveComment(Long userId, Long taskId, CommentRequestDto requestDto){
        User user = userRepository.findByIdOrElseThrow(userId);
        Task task = taskRepository.findByIdOrElseThrow(taskId);

        Comment comment = new Comment(user, task, requestDto.getComment());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Override
    public Page<CommentResponseDto> getCommentsByTaskId(Long taskId, Pageable pageable){
        return commentRepository.findByTaskId(taskId, pageable)
                .map(CommentResponseDto::new);
    }

    @Override
    public Page<CommentResponseDto> getCommentsByUserId(Long userId, Pageable pageable){
        return commentRepository.findByUserId(userId, pageable)
                .map(CommentResponseDto::new);
    }

    @Override
    public CommentResponseDto findCommentById(Long id){
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        return new CommentResponseDto(comment);
    }
    //댓글 수정
    @Override
    public CommentResponseDto updateComment(Long userId, Long id, CommentRequestDto requestDto){
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        if(!comment.getUser().checkisMine(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "타인의 댓글을 수정할 수 없습니다.");
        }

        comment.updateComment(requestDto.getComment());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    //댓글 삭제
    @Override
    public void deleteComment(Long userId, Long id){
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        if(!comment.getUser().checkisMine(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "타인의 댓글을 삭제할 수 없습니다.");
        }
        commentRepository.delete(comment);
    }

}
