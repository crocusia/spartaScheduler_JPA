package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByid(Long id);

    default Task findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다." + id));
    }

    //유저 아이디 또는 수정일이 NULL이면 모든 데이터를 조회, 아니면 조건에 따라 조회
    @Query("SELECT t FROM Task t WHERE (:userId IS NULL OR t.user.id = :userId) " +
            "AND (:updatedAt IS NULL OR t.updatedAt = :updatedAt) " +
            "ORDER BY t.updatedAt DESC")
    List<Task> findTasks(@Param("userId") Long userId,
                         @Param("updatedAt") LocalDateTime updatedAt);

    Page<Task> findWithFilters(Specification<Task> spec, Pageable pageable);
}
