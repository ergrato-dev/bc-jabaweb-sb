package com.bootcamp.taskmanager.exception;

/**
 * Excepción para recursos no encontrados.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿POR QUÉ CREAR EXCEPCIONES PERSONALIZADAS?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Crear excepciones personalizadas permite:
 *
 *   1. CLARIDAD SEMÁNTICA
 *      - ResourceNotFoundException es más claro que RuntimeException
 *      - El código se documenta a sí mismo
 *
 *   2. MANEJO ESPECÍFICO
 *      - Puedes capturar esta excepción específicamente
 *      - El GlobalExceptionHandler puede manejarla de forma especial
 *
 *   3. INFORMACIÓN CONTEXTUAL
 *      - Puedes incluir datos relevantes (nombre del recurso, ID buscado)
 *      - Esto permite mensajes de error más útiles
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * RuntimeException vs Exception
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Esta clase extiende RuntimeException (unchecked exception):
 *   - No requiere try-catch ni throws en la firma del método
 *   - Se propaga automáticamente hasta el GlobalExceptionHandler
 *   - Es el patrón recomendado para errores que el código NO puede recuperar
 *
 * Si extendiera Exception (checked exception):
 *   - Habría que declarar throws en cada método que la use
 *   - O usar try-catch en cada lugar
 *   - Más verboso y menos práctico para APIs REST
 *
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    /**
     * Constructor con información del recurso no encontrado.
     *
     * @param resourceName Nombre del recurso (ej: "Task", "User")
     * @param fieldName Nombre del campo buscado (ej: "id", "email")
     * @param fieldValue Valor buscado (ej: "123", "user@email.com")
     *
     * Ejemplo de uso:
     *   throw new ResourceNotFoundException("Task", "id", "abc123");
     *
     * Mensaje resultante:
     *   "Task not found with id: abc123"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // =========================================================================
    // GETTERS - Para que el GlobalExceptionHandler pueda acceder a los datos
    // =========================================================================

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
