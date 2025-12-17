package com.bootcamp.apidocs.repository;

import com.bootcamp.apidocs.entity.Priority;
import com.bootcamp.apidocs.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserId(UUID userId);

    Page<Task> findByUserId(UUID userId, Pageable pageable);

    List<Task> findByUserIdAndCompleted(UUID userId, Boolean completed);

    List<Task> findByUserIdAndPriority(UUID userId, Priority priority);

    long countByUserId(UUID userId);

    long countByUserIdAndCompleted(UUID userId, Boolean completed);
}
