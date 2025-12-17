package com.bootcamp.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El username es requerido")
    @Size(min = 3, max = 50, message = "Username debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Email debe ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ============================================================
    // TODO 1: Agregar relación @OneToMany con Task
    // ============================================================
    // Instrucciones:
    // - Usar @OneToMany con los siguientes atributos:
    //   - mappedBy = "user" (nombre del campo en Task)
    //   - cascade = CascadeType.ALL (propagar operaciones)
    //   - orphanRemoval = true (eliminar huérfanos)
    //   - fetch = FetchType.LAZY (carga perezosa)
    // - Inicializar como new ArrayList<>()

    // private List<Task> tasks = new ArrayList<>();


    // ============================================================
    // TODO 2: Método helper addTask(Task task)
    // ============================================================
    // Instrucciones:
    // - Agregar task a la lista tasks
    // - Establecer this como el user del task: task.setUser(this)
    // Esto mantiene sincronizados ambos lados de la relación

    // public void addTask(Task task) {
    //
    // }


    // ============================================================
    // TODO 3: Método helper removeTask(Task task)
    // ============================================================
    // Instrucciones:
    // - Remover task de la lista tasks
    // - Establecer null como user del task: task.setUser(null)

    // public void removeTask(Task task) {
    //
    // }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ========== Constructors ==========

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // ========== Getters y Setters ==========

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ============================================================
    // TODO 4: Getter y Setter para tasks
    // ============================================================

    // public List<Task> getTasks() {
    //     return tasks;
    // }

    // public void setTasks(List<Task> tasks) {
    //     this.tasks = tasks;
    // }
}
