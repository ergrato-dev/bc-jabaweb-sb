package com.bootcamp.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El título es requerido")
    @Size(max = 255, message = "Título no puede exceder 255 caracteres")
    @Column(nullable = false)
    private String title;

    @Size(max = 1000, message = "Descripción no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ============================================================
    // TODO 1: Agregar relación @ManyToOne con User
    // ============================================================
    // Instrucciones:
    // - Usar @ManyToOne con fetch = FetchType.LAZY
    // - Usar @JoinColumn con:
    //   - name = "user_id" (nombre de la columna FK)
    //   - nullable = false (toda tarea debe tener usuario)
    // Este es el lado PROPIETARIO de la relación (tiene la FK)

    // private User user;


    // ============================================================
    // TODO 2: Agregar relación @ManyToMany con Category
    // ============================================================
    // Instrucciones:
    // - Usar @ManyToMany con:
    //   - cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    //   - fetch = FetchType.LAZY
    // - Usar @JoinTable con:
    //   - name = "task_category"
    //   - joinColumns = @JoinColumn(name = "task_id")
    //   - inverseJoinColumns = @JoinColumn(name = "category_id")
    // - Inicializar como new HashSet<>()
    // Este es el lado PROPIETARIO de la relación ManyToMany

    // private Set<Category> categories = new HashSet<>();


    // ============================================================
    // TODO 3: Método helper addCategory(Category category)
    // ============================================================
    // Instrucciones:
    // - Agregar category al Set categories
    // - Agregar this al Set tasks de category: category.getTasks().add(this)
    // Esto mantiene sincronizados ambos lados

    // public void addCategory(Category category) {
    //
    // }


    // ============================================================
    // TODO 4: Método helper removeCategory(Category category)
    // ============================================================
    // Instrucciones:
    // - Remover category del Set categories
    // - Remover this del Set tasks de category: category.getTasks().remove(this)

    // public void removeCategory(Category category) {
    //
    // }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ========== Constructors ==========

    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // ========== equals y hashCode ==========

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // ========== Getters y Setters ==========

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ============================================================
    // TODO 5: Getters y Setters para user y categories
    // ============================================================

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public Set<Category> getCategories() {
    //     return categories;
    // }

    // public void setCategories(Set<Category> categories) {
    //     this.categories = categories;
    // }
}
