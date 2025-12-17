package com.bootcamp.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "Nombre no puede exceder 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Color debe ser formato hex (#RRGGBB)")
    @Column(length = 7)
    private String color = "#808080";

    @Size(max = 255, message = "Descripción no puede exceder 255 caracteres")
    private String description;

    // ============================================================
    // TODO 1: Agregar relación @ManyToMany INVERSA con Task
    // ============================================================
    // Instrucciones:
    // - Usar @ManyToMany con:
    //   - mappedBy = "categories" (nombre del campo en Task)
    //   - fetch = FetchType.LAZY
    // - Inicializar como new HashSet<>()
    // Este es el lado INVERSO (no controla la tabla intermedia)

    // private Set<Task> tasks = new HashSet<>();


    // ========== Constructors ==========

    public Category() {
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    // ============================================================
    // TODO 2: Implementar equals() basado en id
    // ============================================================
    // Instrucciones:
    // - Verificar si this == o, retornar true
    // - Verificar si o no es instancia de Category, retornar false
    // - Comparar ids: id != null && Objects.equals(id, category.id)
    // Importante para que Set funcione correctamente

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        // TODO: Completar comparación por id
        return false;
    }

    // ============================================================
    // TODO 3: Implementar hashCode()
    // ============================================================
    // Instrucciones:
    // - Retornar getClass().hashCode()
    // Esto es consistente para entidades JPA con id generado

    @Override
    public int hashCode() {
        // TODO: Implementar
        return 0;
    }

    // ========== Getters y Setters ==========

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ============================================================
    // TODO 4: Getter y Setter para tasks
    // ============================================================

    // public Set<Task> getTasks() {
    //     return tasks;
    // }

    // public void setTasks(Set<Task> tasks) {
    //     this.tasks = tasks;
    // }
}
