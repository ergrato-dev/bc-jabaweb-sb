package com.bootcamp.todo.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa una Tarea (Task).
 *
 * Esta clase es un POJO (Plain Old Java Object) que será serializado
 * automáticamente a JSON por Jackson (incluido en Spring Web).
 *
 * Requisitos para que Jackson funcione:
 * 1. Constructor vacío (sin argumentos)
 * 2. Getters para las propiedades que quieres exponer
 * 3. Setters si necesitas deserializar (recibir JSON)
 */
public class Task {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    // TODO 1: Agregar el atributo 'completed' de tipo boolean
    // Este atributo indica si la tarea está completada o no
    // private ??? completed;


    /**
     * Constructor vacío - REQUERIDO por Jackson para deserialización
     */
    public Task() {
        this.createdAt = LocalDateTime.now();
    }

    // TODO 2: Crear constructor con parámetros (id, title, description)
    // - Debe asignar los valores a los atributos
    // - Debe inicializar createdAt con LocalDateTime.now()
    // - Debe inicializar completed en false
    //
    // public Task(Long id, String title, String description) {
    //     ???
    // }


    // =========================================================================
    // GETTERS - Jackson los usa para serializar a JSON
    // =========================================================================

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // TODO 3: Agregar getter para 'completed'
    // Recuerda: para boolean, el getter puede ser isCompleted() o getCompleted()
    // public ??? ???() {
    //     return ???;
    // }


    // =========================================================================
    // SETTERS - Jackson los usa para deserializar desde JSON
    // =========================================================================

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // TODO 4: Agregar setter para 'completed'
    // public void ???(??? completed) {
    //     ???
    // }


    // =========================================================================
    // Método útil para depuración
    // =========================================================================

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                // TODO 5: Agregar 'completed' al toString()
                // ", completed=" + ??? +
                '}';
    }
}
