package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByid(Long id);

    //유저 아이디 또는 수정일이 NULL이면 모든 데이터를 조회, 아니면 조건에 따라 조회
    @Query("SELECT t FROM Task t WHERE (:userId IS NULL OR t.user.id = :userId) " +
            "AND (:updatedAt IS NULL OR t.updatedAt = :updatedAt) " +
            "ORDER BY t.updatedAt DESC")
    List<Task> findTasks(@Param("userId") Long userId,
                         @Param("updatedAt") LocalDateTime updatedAt);
}
