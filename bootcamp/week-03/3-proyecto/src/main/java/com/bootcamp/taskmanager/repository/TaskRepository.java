package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio de tareas - Capa de DATOS.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES LA CAPA REPOSITORY?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * La capa Repository es responsable de:
 *   - Acceso a la fuente de datos (BD, memoria, archivo, API externa)
 *   - Operaciones CRUD (Create, Read, Update, Delete)
 *   - Consultas específicas
 *
 * ¿QUÉ NO DEBE HACER?
 *   - Lógica de negocio (eso va en Service)
 *   - Validaciones de negocio (eso va en Service)
 *   - Manejo de HTTP (eso va en Controller)
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * @Repository
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @Repository es una especialización de @Component que:
 *   - Indica semánticamente que esta clase accede a datos
 *   - Habilita traducción de excepciones de persistencia
 *   - Permite que Spring la detecte y cree un bean automáticamente
 *
 * En la Semana 04, reemplazaremos esta clase con una INTERFACE que
 * extienda JpaRepository<Task, String> y Spring Data JPA implementará
 * todos los métodos automáticamente.
 *
 */
@Repository
public class TaskRepository {

    /**
     * Almacenamiento en memoria usando ConcurrentHashMap.
     *
     * ConcurrentHashMap es thread-safe, lo que significa que múltiples
     * peticiones HTTP pueden acceder simultáneamente sin problemas.
     *
     * En Semana 04 esto será reemplazado por PostgreSQL.
     */
    private final Map<String, Task> storage = new ConcurrentHashMap<>();

    // =========================================================================
    // OPERACIONES CRUD
    // =========================================================================

    /**
     * Guarda una tarea (crear o actualizar).
     *
     * @param task La tarea a guardar
     * @return La tarea guardada
     */
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    /**
     * Busca una tarea por ID.
     *
     * @param id El ID de la tarea
     * @return Optional con la tarea si existe, vacío si no
     */
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Retorna todas las tareas.
     *
     * @return Lista de todas las tareas
     */
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Elimina una tarea por ID.
     *
     * @param id El ID de la tarea a eliminar
     */
    public void deleteById(String id) {
        storage.remove(id);
    }

    /**
     * Verifica si existe una tarea con el ID dado.
     *
     * @param id El ID a verificar
     * @return true si existe, false si no
     */
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    // =========================================================================
    // TODO 5: Implementar método findByTitle (OPCIONAL)
    // =========================================================================
    //
    // Busca tareas cuyo título contenga el texto dado (case insensitive).
    //
    // Firma del método:
    //   public List<Task> findByTitleContaining(String title)
    //
    // Implementación:
    //   return storage.values().stream()
    //       .filter(task -> task.getTitle().toLowerCase()
    //           .contains(title.toLowerCase()))
    //       .collect(Collectors.toList());
    //
    // 💡 Este tipo de métodos de búsqueda serán AUTOMÁTICOS con Spring Data JPA
    //    en la Semana 04. Solo defines la firma del método y JPA genera la query.
    //

}
