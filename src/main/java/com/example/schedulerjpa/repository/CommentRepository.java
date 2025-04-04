package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Comment;
import com.example.schedulerjpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다." + id));
    }

    //특정 일정에 속한 댓글을 페이징
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);
    //특정 유저에 속한 댓글을 페이징
    Page<Comment> findByUserId(Long userId, Pageable pageable);

    Long countByTaskId(Long taskId);

    //많은 일정에 대해서 댓글 개수를 조회해야할 때
    @Query("SELECT c.task.id, COUNT(c) FROM Comment c WHERE c.task.id IN :taskIds GROUP BY c.task.id")
    List<Object[]> countByTaskIds(@Param("taskIds") List<Long> taskIds);

    Long countByUserId(Long userId);
}
