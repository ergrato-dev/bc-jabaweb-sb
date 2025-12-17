# 📋 Soluciones - Semana 05

> ⚠️ **SOLO PARA INSTRUCTORES** - No compartir con estudiantes

Este documento contiene las soluciones completas para todos los TODOs del proyecto.

---

## Entidades

### User.java - TODOs 1-4

```java
// TODO 1: Relación @OneToMany
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
private List<Task> tasks = new ArrayList<>();

// TODO 2: Método addTask
public void addTask(Task task) {
    tasks.add(task);
    task.setUser(this);
}

// TODO 3: Método removeTask
public void removeTask(Task task) {
    tasks.remove(task);
    task.setUser(null);
}

// TODO 4: Getter y Setter
public List<Task> getTasks() {
    return tasks;
}

public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
}
```

### Task.java - TODOs 1-5

```java
// TODO 1: Relación @ManyToOne
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

// TODO 2: Relación @ManyToMany
@ManyToMany(
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.LAZY
)
@JoinTable(
    name = "task_category",
    joinColumns = @JoinColumn(name = "task_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private Set<Category> categories = new HashSet<>();

// TODO 3: Método addCategory
public void addCategory(Category category) {
    this.categories.add(category);
    category.getTasks().add(this);
}

// TODO 4: Método removeCategory
public void removeCategory(Category category) {
    this.categories.remove(category);
    category.getTasks().remove(this);
}

// TODO 5: Getters y Setters
public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public Set<Category> getCategories() {
    return categories;
}

public void setCategories(Set<Category> categories) {
    this.categories = categories;
}
```

### Category.java - TODOs 1-4

```java
// TODO 1: Relación @ManyToMany inversa
@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
private Set<Task> tasks = new HashSet<>();

// TODO 2: equals()
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category category)) return false;
    return id != null && Objects.equals(id, category.id);
}

// TODO 3: hashCode()
@Override
public int hashCode() {
    return getClass().hashCode();
}

// TODO 4: Getter y Setter
public Set<Task> getTasks() {
    return tasks;
}

public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
}
```

---

## Repositories

### UserRepository.java - TODOs 1-6

```java
// TODO 1
Optional<User> findByUsername(String username);

// TODO 2
Optional<User> findByEmail(String email);

// TODO 3
@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
Optional<User> findByIdWithTasks(@Param("id") UUID id);

// TODO 4
@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks")
List<User> findAllWithTasks();

// TODO 5
boolean existsByUsername(String username);

// TODO 6
boolean existsByEmail(String email);
```

### TaskRepository.java - TODOs 1-8

```java
// TODO 1
List<Task> findByUserId(UUID userId);

// TODO 2
List<Task> findByUserUsername(String username);

// TODO 3
@Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.id = :id")
Optional<Task> findByIdWithUser(@Param("id") UUID id);

// TODO 4
@Query("SELECT t FROM Task t LEFT JOIN FETCH t.categories WHERE t.id = :id")
Optional<Task> findByIdWithCategories(@Param("id") UUID id);

// TODO 5
@Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.user LEFT JOIN FETCH t.categories WHERE t.id = :id")
Optional<Task> findByIdComplete(@Param("id") UUID id);

// TODO 6
@Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.categories WHERE t.user.id = :userId")
List<Task> findByUserIdWithCategories(@Param("userId") UUID userId);

// TODO 7
@Query("SELECT t FROM Task t JOIN t.categories c WHERE c.id = :categoryId")
List<Task> findByCategoryId(@Param("categoryId") UUID categoryId);

// TODO 8
List<Task> findByUserIdAndCompleted(UUID userId, Boolean completed);
```

### CategoryRepository.java - TODOs 1-5

```java
// TODO 1
Optional<Category> findByName(String name);

// TODO 2
List<Category> findByNameContainingIgnoreCase(String name);

// TODO 3
@Query("SELECT c FROM Category c LEFT JOIN FETCH c.tasks WHERE c.id = :id")
Optional<Category> findByIdWithTasks(@Param("id") UUID id);

// TODO 4
@Query("SELECT DISTINCT c FROM Category c JOIN c.tasks")
List<Category> findCategoriesWithTasks();

// TODO 5
boolean existsByName(String name);
```

---

## DTOs

### UserDTO.java - TODO 1

```java
public static UserDTO fromEntity(User user) {
    return new UserDTO(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getCreatedAt(),
        user.getTasks() != null ? user.getTasks().size() : 0
    );
}
```

### TaskDTO.java - TODOs 1-2

```java
// TODO 1
public static TaskDTO fromEntity(Task task) {
    return new TaskDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getCompleted(),
        task.getCreatedAt(),
        task.getUpdatedAt(),
        task.getUser() != null ? task.getUser().getId() : null,
        task.getUser() != null ? task.getUser().getUsername() : null,
        task.getCategories() != null && !task.getCategories().isEmpty()
            ? task.getCategories().stream()
                .map(CategoryDTO::simpleFromEntity)
                .toList()
            : List.of()
    );
}

// TODO 2
public static TaskDTO simpleFromEntity(Task task) {
    return new TaskDTO(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getCompleted(),
        task.getCreatedAt(),
        task.getUpdatedAt(),
        task.getUser() != null ? task.getUser().getId() : null,
        task.getUser() != null ? task.getUser().getUsername() : null,
        List.of()
    );
}
```

### CategoryDTO.java - TODOs 1-2

```java
// TODO 1
public static CategoryDTO fromEntity(Category category) {
    return new CategoryDTO(
        category.getId(),
        category.getName(),
        category.getColor(),
        category.getDescription(),
        category.getTasks() != null ? category.getTasks().size() : 0
    );
}

// TODO 2
public static CategoryDTO simpleFromEntity(Category category) {
    return new CategoryDTO(
        category.getId(),
        category.getName(),
        category.getColor(),
        category.getDescription(),
        0
    );
}
```

---

## Services

### UserService.java - TODOs 1-5

```java
// TODO 1
@Transactional(readOnly = true)
public List<UserDTO> findAll() {
    return userRepository.findAll().stream()
        .map(UserDTO::fromEntity)
        .toList();
}

// TODO 2
@Transactional(readOnly = true)
public UserDTO findById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    return UserDTO.fromEntity(user);
}

// TODO 3
@Transactional(readOnly = true)
public UserDTO findByIdWithTasks(UUID id) {
    User user = userRepository.findByIdWithTasks(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    return UserDTO.fromEntity(user);
}

// TODO 4
@Transactional
public UserDTO create(CreateUserRequest request) {
    if (userRepository.existsByUsername(request.username())) {
        throw new RuntimeException("Username ya existe: " + request.username());
    }
    if (userRepository.existsByEmail(request.email())) {
        throw new RuntimeException("Email ya existe: " + request.email());
    }
    User user = new User(request.username(), request.email());
    user = userRepository.save(user);
    return UserDTO.fromEntity(user);
}

// TODO 5
@Transactional
public void delete(UUID id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("Usuario no encontrado: " + id);
    }
    userRepository.deleteById(id);
}
```

### TaskService.java - TODOs 1-8

```java
// TODO 1
@Transactional(readOnly = true)
public List<TaskDTO> findAll() {
    return taskRepository.findAll().stream()
        .map(TaskDTO::simpleFromEntity)
        .toList();
}

// TODO 2
@Transactional(readOnly = true)
public List<TaskDTO> findByUserId(UUID userId) {
    return taskRepository.findByUserIdWithCategories(userId).stream()
        .map(TaskDTO::fromEntity)
        .toList();
}

// TODO 3
@Transactional(readOnly = true)
public TaskDTO findById(UUID id) {
    Task task = taskRepository.findByIdComplete(id)
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada: " + id));
    return TaskDTO.fromEntity(task);
}

// TODO 4
@Transactional
public TaskDTO create(CreateTaskRequest request) {
    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.userId()));

    Task task = new Task(request.title(), request.description());
    task.setUser(user);

    if (request.categoryIds() != null) {
        for (UUID catId : request.categoryIds()) {
            Category cat = categoryRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + catId));
            task.addCategory(cat);
        }
    }

    task = taskRepository.save(task);
    return TaskDTO.fromEntity(task);
}

// TODO 5
@Transactional
public TaskDTO update(UUID id, UpdateTaskRequest request) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada: " + id));

    if (request.title() != null) task.setTitle(request.title());
    if (request.description() != null) task.setDescription(request.description());
    if (request.completed() != null) task.setCompleted(request.completed());

    task = taskRepository.save(task);
    return TaskDTO.fromEntity(task);
}

// TODO 6
@Transactional
public TaskDTO addCategory(UUID taskId, UUID categoryId) {
    Task task = taskRepository.findByIdWithCategories(taskId)
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada: " + taskId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

    task.addCategory(category);
    task = taskRepository.save(task);
    return TaskDTO.fromEntity(task);
}

// TODO 7
@Transactional
public TaskDTO removeCategory(UUID taskId, UUID categoryId) {
    Task task = taskRepository.findByIdWithCategories(taskId)
        .orElseThrow(() -> new RuntimeException("Tarea no encontrada: " + taskId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

    task.removeCategory(category);
    task = taskRepository.save(task);
    return TaskDTO.fromEntity(task);
}

// TODO 8
@Transactional
public void delete(UUID id) {
    if (!taskRepository.existsById(id)) {
        throw new RuntimeException("Tarea no encontrada: " + id);
    }
    taskRepository.deleteById(id);
}
```

### CategoryService.java - TODOs 1-6

```java
// TODO 1
@Transactional(readOnly = true)
public List<CategoryDTO> findAll() {
    return categoryRepository.findAll().stream()
        .map(CategoryDTO::simpleFromEntity)
        .toList();
}

// TODO 2
@Transactional(readOnly = true)
public CategoryDTO findById(UUID id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    return CategoryDTO.simpleFromEntity(category);
}

// TODO 3
@Transactional(readOnly = true)
public CategoryDTO findByIdWithTasks(UUID id) {
    Category category = categoryRepository.findByIdWithTasks(id)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    return CategoryDTO.fromEntity(category);
}

// TODO 4
@Transactional
public CategoryDTO create(CreateCategoryRequest request) {
    if (categoryRepository.existsByName(request.name())) {
        throw new RuntimeException("Categoría ya existe: " + request.name());
    }
    Category category = new Category(request.name(), request.color());
    category.setDescription(request.description());
    category = categoryRepository.save(category);
    return CategoryDTO.simpleFromEntity(category);
}

// TODO 5
@Transactional
public CategoryDTO update(UUID id, CreateCategoryRequest request) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));

    if (request.name() != null) category.setName(request.name());
    if (request.color() != null) category.setColor(request.color());
    if (request.description() != null) category.setDescription(request.description());

    category = categoryRepository.save(category);
    return CategoryDTO.simpleFromEntity(category);
}

// TODO 6
@Transactional
public void delete(UUID id) {
    if (!categoryRepository.existsById(id)) {
        throw new RuntimeException("Categoría no encontrada: " + id);
    }
    categoryRepository.deleteById(id);
}
```

---

## Controllers

### UserController.java - TODOs 1-5

```java
// TODO 1
@GetMapping
public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.ok(userService.findAll());
}

// TODO 2
@GetMapping("/{id}")
public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findById(id));
}

// TODO 3
@GetMapping("/{id}/tasks")
public ResponseEntity<UserDTO> findByIdWithTasks(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findByIdWithTasks(id));
}

// TODO 4
@PostMapping
public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequest request) {
    UserDTO created = userService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

// TODO 5
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
}
```

### TaskController.java - TODOs 1-8

```java
// TODO 1
@GetMapping
public ResponseEntity<List<TaskDTO>> findAll() {
    return ResponseEntity.ok(taskService.findAll());
}

// TODO 2
@GetMapping("/{id}")
public ResponseEntity<TaskDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(taskService.findById(id));
}

// TODO 3
@GetMapping("/user/{userId}")
public ResponseEntity<List<TaskDTO>> findByUserId(@PathVariable UUID userId) {
    return ResponseEntity.ok(taskService.findByUserId(userId));
}

// TODO 4
@PostMapping
public ResponseEntity<TaskDTO> create(@Valid @RequestBody CreateTaskRequest request) {
    TaskDTO created = taskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

// TODO 5
@PutMapping("/{id}")
public ResponseEntity<TaskDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequest request) {
    return ResponseEntity.ok(taskService.update(id, request));
}

// TODO 6
@PostMapping("/{id}/categories/{categoryId}")
public ResponseEntity<TaskDTO> addCategory(@PathVariable UUID id, @PathVariable UUID categoryId) {
    return ResponseEntity.ok(taskService.addCategory(id, categoryId));
}

// TODO 7
@DeleteMapping("/{id}/categories/{categoryId}")
public ResponseEntity<TaskDTO> removeCategory(@PathVariable UUID id, @PathVariable UUID categoryId) {
    return ResponseEntity.ok(taskService.removeCategory(id, categoryId));
}

// TODO 8
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
}
```

### CategoryController.java - TODOs 1-6

```java
// TODO 1
@GetMapping
public ResponseEntity<List<CategoryDTO>> findAll() {
    return ResponseEntity.ok(categoryService.findAll());
}

// TODO 2
@GetMapping("/{id}")
public ResponseEntity<CategoryDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(categoryService.findById(id));
}

// TODO 3
@GetMapping("/{id}/tasks")
public ResponseEntity<CategoryDTO> findByIdWithTasks(@PathVariable UUID id) {
    return ResponseEntity.ok(categoryService.findByIdWithTasks(id));
}

// TODO 4
@PostMapping
public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CreateCategoryRequest request) {
    CategoryDTO created = categoryService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

// TODO 5
@PutMapping("/{id}")
public ResponseEntity<CategoryDTO> update(@PathVariable UUID id, @Valid @RequestBody CreateCategoryRequest request) {
    return ResponseEntity.ok(categoryService.update(id, request));
}

// TODO 6
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
    categoryService.delete(id);
    return ResponseEntity.noContent().build();
}
```
