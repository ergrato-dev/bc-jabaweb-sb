package com.bootcamp.taskmanager.exception;

// TODO 15: Importar clases necesarias para el manejo global de excepciones
//
// Importaciones necesarias:
//   import org.springframework.http.HttpStatus;
//   import org.springframework.http.ResponseEntity;
//   import org.springframework.web.bind.MethodArgumentNotValidException;
//   import org.springframework.web.bind.annotation.ExceptionHandler;
//   import org.springframework.web.bind.annotation.RestControllerAdvice;
//   import org.springframework.web.context.request.WebRequest;
//   import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejador global de excepciones para toda la aplicación.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES @RestControllerAdvice?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @RestControllerAdvice combina:
 *   - @ControllerAdvice: Aplica a TODOS los controllers
 *   - @ResponseBody: Las respuestas se serializan a JSON automáticamente
 *
 * Cuando cualquier controller lanza una excepción:
 *   1. Spring busca un @ExceptionHandler que coincida
 *   2. Si existe, ejecuta ese método
 *   3. El método retorna una ResponseEntity con el error formateado
 *
 * VENTAJAS:
 *   - Centraliza TODO el manejo de errores
 *   - Garantiza respuestas de error consistentes
 *   - Evita try-catch en cada controller
 *   - Más fácil de mantener y modificar
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ORDEN DE PRECEDENCIA
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Spring elige el handler MÁS ESPECÍFICO:
 *   1. ResourceNotFoundException (específico) → 404
 *   2. MethodArgumentNotValidException (validación) → 400
 *   3. Exception (genérico, catch-all) → 500
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================================================
    // TODO 16: Manejar ResourceNotFoundException
    // =========================================================================
    //
    // Este handler captura cuando buscamos un recurso que no existe.
    // Debe retornar HTTP 404 (Not Found).
    //
    // DESCOMENTA y completa:
    //
    // @ExceptionHandler(ResourceNotFoundException.class)
    // public ResponseEntity<ErrorResponse> handleResourceNotFound(
    //         ResourceNotFoundException ex,
    //         HttpServletRequest request) {
    //
    //     ErrorResponse error = new ErrorResponse(
    //         ???,                              // status code (404)
    //         "Not Found",                      // error type
    //         ex.getMessage(),                  // mensaje de la excepción
    //         request.getRequestURI()           // path de la petición
    //     );
    //
    //     return ResponseEntity
    //         .status(HttpStatus.???)           // NOT_FOUND
    //         .body(error);
    // }


    // =========================================================================
    // TODO 17: Manejar errores de validación (Bean Validation)
    // =========================================================================
    //
    // Este handler captura cuando @Valid falla en el controller.
    // Se lanza MethodArgumentNotValidException cuando el DTO tiene errores.
    // Debe retornar HTTP 400 (Bad Request).
    //
    // El mensaje debe incluir TODOS los errores de validación encontrados.
    //
    // DESCOMENTA y completa:
    //
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<ErrorResponse> handleValidationErrors(
    //         MethodArgumentNotValidException ex,
    //         HttpServletRequest request) {
    //
    //     // Extraer todos los mensajes de error de validación
    //     String errors = ex.getBindingResult()
    //         .getFieldErrors()
    //         .stream()
    //         .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
    //         .collect(java.util.stream.Collectors.joining("; "));
    //
    //     ErrorResponse error = new ErrorResponse(
    //         ???,                              // status code (400)
    //         "Bad Request",                    // error type
    //         errors,                           // mensajes de validación
    //         request.getRequestURI()           // path de la petición
    //     );
    //
    //     return ResponseEntity
    //         .status(HttpStatus.???)           // BAD_REQUEST
    //         .body(error);
    // }


    // =========================================================================
    // TODO 18: Handler genérico para cualquier otra excepción
    // =========================================================================
    //
    // Este es el "catch-all" para excepciones no manejadas específicamente.
    // Debe retornar HTTP 500 (Internal Server Error).
    //
    // ⚠️  IMPORTANTE - SEGURIDAD:
    //     NUNCA expongas el mensaje real de la excepción al cliente.
    //     Podría revelar información sensible (rutas, SQL, configuración).
    //
    //     ✅ CORRECTO: mensaje genérico al cliente, log interno detallado
    //     ❌ INCORRECTO: ex.getMessage() directo al cliente
    //
    // DESCOMENTA y completa:
    //
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponse> handleGenericException(
    //         Exception ex,
    //         HttpServletRequest request) {
    //
    //     // Log interno para debugging (esto SÍ incluye detalles)
    //     // En producción usarías: log.error("Error interno: ", ex);
    //     System.err.println("Error interno: " + ex.getMessage());
    //     ex.printStackTrace();
    //
    //     // Mensaje GENÉRICO para el cliente (sin detalles sensibles)
    //     ErrorResponse error = new ErrorResponse(
    //         ???,                              // status code (500)
    //         "Internal Server Error",          // error type
    //         "Ha ocurrido un error interno. Por favor, contacte al administrador.",
    //         request.getRequestURI()
    //     );
    //
    //     return ResponseEntity
    //         .status(HttpStatus.???)           // INTERNAL_SERVER_ERROR
    //         .body(error);
    // }

}
