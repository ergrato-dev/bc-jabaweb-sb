# 🔑 Soluciones del Proyecto - Semana 04

> ⚠️ **SOLO PARA INSTRUCTORES**: No compartir con estudiantes.

---

## Task.java - Solución Completa

```java
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ... constructores y getters/setters ...

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
```

---

## TaskRepository.java - Solución Completa

```java
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    long countByCompleted(boolean completed);

    boolean existsByTitle(String title);
}
```

---

## TaskServiceImpl.java - Solución Completa

```java
@Override
@Transactional
public TaskResponse create(TaskRequest request) {
    Task task = new Task();
    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setCompleted(false);

    Task saved = taskRepository.save(task);
    return toResponse(saved);
}

@Override
@Transactional(readOnly = true)
public TaskResponse findById(UUID id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    return toResponse(task);
}

@Override
@Transactional(readOnly = true)
public List<TaskResponse> findAll() {
    return taskRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
}

@Override
@Transactional
public TaskResponse update(UUID id, TaskRequest request) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

    task.setTitle(request.title());
    task.setDescription(request.description());
    if (request.completed() != null) {
        task.setCompleted(request.completed());
    }

    Task updated = taskRepository.save(task);
    return toResponse(updated);
}

@Override
@Transactional
public void delete(UUID id) {
    if (!taskRepository.existsById(id)) {
        throw new ResourceNotFoundException("Task", "id", id);
    }
    taskRepository.deleteById(id);
}
```

---

## TaskController.java - Solución Completa

```java
@PostMapping
public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
    TaskResponse response = taskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@GetMapping("/{id}")
public ResponseEntity<TaskResponse> findById(@PathVariable UUID id) {
    TaskResponse response = taskService.findById(id);
    return ResponseEntity.ok(response);
}

@GetMapping
public ResponseEntity<List<TaskResponse>> findAll() {
    List<TaskResponse> response = taskService.findAll();
    return ResponseEntity.ok(response);
}

@PutMapping("/{id}")
public ResponseEntity<TaskResponse> update(
        @PathVariable UUID id,
        @Valid @RequestBody TaskRequest request) {
    TaskResponse response = taskService.update(id, request);
    return ResponseEntity.ok(response);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
}
```
