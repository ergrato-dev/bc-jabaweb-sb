package com.bootcamp.taskmanager.dto;

// TODO 2: Importar anotaciones de Bean Validation
//
// Las anotaciones de validación vienen del paquete jakarta.validation.constraints
// Importa las que necesites:
//   import jakarta.validation.constraints.NotBlank;
//   import jakarta.validation.constraints.Size;
//
// 📚 Lista completa de anotaciones disponibles:
//    https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html#builtinconstraints

/**
 * DTO para recibir datos de creación/actualización de una tarea.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES UN DTO?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * DTO = Data Transfer Object
 *
 * Es un objeto que SOLO transporta datos entre capas. NO tiene lógica de negocio.
 *
 * ¿Por qué usar DTOs en lugar de la entidad directamente?
 *
 *   1. SEGURIDAD: El cliente NO puede enviar campos que no debería
 *      - Sin DTO: El cliente podría enviar "id", "createdAt" y modificarlos
 *      - Con DTO: Solo puede enviar "title" y "description"
 *
 *   2. DESACOPLAMIENTO: Cambios en la entidad no afectan la API
 *      - Puedes agregar campos internos a Task sin exponer al cliente
 *
 *   3. VALIDACIÓN: Las validaciones de API van en el DTO
 *      - La entidad tendrá validaciones de BD (en semana 04)
 *      - El DTO tiene validaciones de entrada del usuario
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * FLUJO DE DATOS
 * ═══════════════════════════════════════════════════════════════════════════
 *
 *   Cliente                Controller            Service              Repository
 *      │                       │                    │                      │
 *      │  JSON Request         │                    │                      │
 *      │  {title, desc}        │                    │                      │
 *      │ ───────────────────▶  │                    │                      │
 *      │                       │                    │                      │
 *      │                  TaskRequest               │                      │
 *      │                  (validado)                │                      │
 *      │                       │ ─────────────────▶ │                      │
 *      │                       │                    │                      │
 *      │                       │               Task (Entity)               │
 *      │                       │                    │ ───────────────────▶ │
 *      │                       │                    │                      │
 */
public class TaskRequest {

    // =========================================================================
    // TODO 3: Agregar validaciones al campo 'title'
    // =========================================================================
    //
    // El título es OBLIGATORIO y debe tener entre 3 y 100 caracteres.
    //
    // Anotaciones a usar:
    //   @NotBlank(message = "El título es requerido")
    //   @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    //
    // ¿Por qué @NotBlank y no @NotNull?
    //   - @NotNull: Solo verifica que no sea null
    //   - @NotBlank: Verifica que no sea null, no esté vacío, y no sea solo espacios
    //   - Para strings, @NotBlank es más seguro
    //
    // DESCOMENTA y completa:
    // @NotBlank(message = "???")
    // @Size(min = ???, max = ???, message = "???")
    private String title;


    // =========================================================================
    // TODO 4: Agregar validaciones al campo 'description' (opcional)
    // =========================================================================
    //
    // La descripción es OPCIONAL pero si se envía, máximo 500 caracteres.
    //
    // Anotación a usar:
    //   @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    //
    // 💡 NOTA: NO usamos @NotBlank porque el campo es opcional
    //
    // DESCOMENTA y completa:
    // @Size(max = ???, message = "???")
    private String description;


    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    /**
     * Constructor vacío - necesario para deserialización JSON
     */
    public TaskRequest() {
    }

    /**
     * Constructor con todos los campos
     */
    public TaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

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
}
