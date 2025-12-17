package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz del servicio de tareas.
 */
public interface TaskService {

    TaskResponse create(TaskRequest request);

    TaskResponse findById(UUID id);

    List<TaskResponse> findAll();

    TaskResponse update(UUID id, TaskRequest request);

    void delete(UUID id);
}
