package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.UserDTO;
import com.bootcamp.taskmanager.dto.request.CreateUserRequest;
import com.bootcamp.taskmanager.entity.User;
import com.bootcamp.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ============================================================
    // TODO 1: Listar todos los usuarios
    // ============================================================
    // Instrucciones:
    // - Usar userRepository.findAll()
    // - Mapear cada User a UserDTO usando stream()
    // - Usar @Transactional(readOnly = true)

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        // TODO: Implementar
        // return userRepository.findAll().stream()
        //     .map(UserDTO::fromEntity)
        //     .toList();
        return List.of();
    }


    // ============================================================
    // TODO 2: Buscar usuario por ID
    // ============================================================
    // Instrucciones:
    // - Usar userRepository.findById(id)
    // - Lanzar excepción si no existe
    // - Mapear a UserDTO

    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        // TODO: Implementar
        // User user = userRepository.findById(id)
        //     .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        // return UserDTO.fromEntity(user);
        return null;
    }


    // ============================================================
    // TODO 3: Buscar usuario con sus tareas (JOIN FETCH)
    // ============================================================
    // Instrucciones:
    // - Usar userRepository.findByIdWithTasks(id)
    // - Esto evita el problema N+1
    // - Mapear a UserDTO (taskCount se calculará correctamente)

    @Transactional(readOnly = true)
    public UserDTO findByIdWithTasks(UUID id) {
        // TODO: Implementar usando método con JOIN FETCH
        return null;
    }


    // ============================================================
    // TODO 4: Crear usuario
    // ============================================================
    // Instrucciones:
    // - Verificar que username no exista (existsByUsername)
    // - Verificar que email no exista (existsByEmail)
    // - Crear nueva entidad User
    // - Guardar con userRepository.save()
    // - Retornar UserDTO

    @Transactional
    public UserDTO create(CreateUserRequest request) {
        // TODO: Validar que no exista username/email
        // if (userRepository.existsByUsername(request.username())) {
        //     throw new RuntimeException("Username ya existe: " + request.username());
        // }

        // TODO: Crear y guardar usuario
        // User user = new User(request.username(), request.email());
        // user = userRepository.save(user);
        // return UserDTO.fromEntity(user);
        return null;
    }


    // ============================================================
    // TODO 5: Eliminar usuario
    // ============================================================
    // Instrucciones:
    // - Verificar que exista
    // - Usar userRepository.deleteById(id)
    // - Con cascade = ALL, sus tareas también se eliminan

    @Transactional
    public void delete(UUID id) {
        // TODO: Implementar
        // if (!userRepository.existsById(id)) {
        //     throw new RuntimeException("Usuario no encontrado: " + id);
        // }
        // userRepository.deleteById(id);
    }
}
