# Guía de Contribución

¡Gracias por tu interés en contribuir al Bootcamp Java Web con Spring Boot! 🎉

Este documento proporciona las directrices para contribuir al proyecto.

## 📋 Tabla de Contenidos

- [Código de Conducta](#código-de-conducta)
- [¿Cómo puedo contribuir?](#cómo-puedo-contribuir)
- [Configuración del entorno](#configuración-del-entorno)
- [Flujo de trabajo](#flujo-de-trabajo)
- [Guías de estilo](#guías-de-estilo)
- [Commits](#commits)

---

## Código de Conducta

Este proyecto y todos sus participantes están sujetos al [Código de Conducta](CODE_OF_CONDUCT.md). Al participar, se espera que respetes este código.

---

## ¿Cómo puedo contribuir?

### 🐛 Reportar Bugs

Si encuentras un bug:

1. **Busca** si ya existe un issue similar
2. Si no existe, **crea uno nuevo** usando la plantilla de bug
3. Incluye:
   - Descripción clara del problema
   - Pasos para reproducir
   - Comportamiento esperado vs actual
   - Screenshots si aplica
   - Entorno (OS, versión de Docker, etc.)

### 💡 Sugerir Mejoras

Para proponer nuevas funcionalidades o mejoras:

1. **Busca** si ya existe una propuesta similar
2. **Crea un issue** usando la plantilla de feature request
3. Explica claramente:
   - El problema que resuelve
   - La solución propuesta
   - Alternativas consideradas

### 📚 Mejorar Documentación

- Corrección de errores ortográficos o gramaticales
- Clarificar instrucciones confusas
- Agregar ejemplos adicionales
- Traducir contenido

### ✨ Agregar Contenido

- Nuevos ejercicios prácticos
- Ejemplos de código
- Diagramas y recursos visuales (SVG, tema dark, sin degradados)
- Videos tutoriales

### 🔧 Código

- Corrección de bugs
- Mejoras en proyectos de ejemplo
- Nuevos tests
- Optimizaciones

---

## Configuración del entorno

### Prerrequisitos

- Docker Desktop 24+
- VS Code con extensiones recomendadas
- Git

### Pasos

```bash
# 1. Fork del repositorio en GitHub

# 2. Clonar tu fork
git clone https://github.com/TU-USUARIO/bc-javaweb-sb.git
cd bc-javaweb-sb

# 3. Agregar upstream
git remote add upstream https://github.com/ORIGINAL/bc-javaweb-sb.git

# 4. Abrir en VS Code
code .

# 5. Instalar extensiones recomendadas
# Ctrl+Shift+P → "Extensions: Show Recommended Extensions"
```

---

## Flujo de trabajo

### 1. Crear una rama

```bash
# Actualizar main
git checkout main
git pull upstream main

# Crear rama para tu contribución
git checkout -b tipo/descripcion-corta

# Ejemplos:
# feat/add-week-02-exercises
# fix/typo-in-readme
# docs/improve-docker-guide
```

### 2. Hacer cambios

- Sigue las [guías de estilo](#guías-de-estilo)
- Asegúrate de que los tests pasen
- Actualiza la documentación si es necesario

### 3. Commit

Usa [Conventional Commits](https://www.conventionalcommits.org/):

```bash
git commit -m "tipo(alcance): descripción"

# Ejemplos:
git commit -m "feat(week-03): add JPA exercises"
git commit -m "fix(week-01): correct docker-compose port"
git commit -m "docs(readme): update prerequisites"
```

### 4. Push y Pull Request

```bash
git push origin tu-rama
```

Luego abre un Pull Request en GitHub usando la plantilla.

---

## Guías de estilo

### 📝 Markdown

- Usar headers apropiados (H1 para título, H2 para secciones principales)
- Bloques de código con sintaxis highlighting
- Emojis para mejorar legibilidad
- Enlaces a recursos externos cuando sea relevante

### ☕ Código Java

- **Nomenclatura en inglés** (OBLIGATORIO)
  - Clases: `UserService`, `OrderController`
  - Métodos: `findById()`, `createUser()`
  - Variables: `userName`, `totalAmount`
- Convenciones Java (camelCase, PascalCase)
- Comentarios Javadoc en métodos públicos
- Validaciones con Bean Validation

### 🐳 Docker

- Usar imágenes oficiales y específicas (ej: `eclipse-temurin:21-jre-alpine`)
- Multi-stage builds para optimización
- No hardcodear credenciales

### 🎨 Recursos Visuales

- Formato SVG preferido
- Tema dark (fondo oscuro)
- Sin degradados
- Fuentes sin serifas

---

## Commits

### Tipos permitidos

| Tipo | Descripción |
|------|-------------|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Cambios en documentación |
| `style` | Formato, espacios, etc. (no afecta código) |
| `refactor` | Refactorización de código |
| `test` | Agregar o corregir tests |
| `chore` | Tareas de mantenimiento |

### Alcances sugeridos

- `week-01` a `week-09` - Contenido de semanas específicas
- `readme` - README principal
- `docker` - Configuración Docker
- `ci` - CI/CD
- `deps` - Dependencias

### Ejemplos

```bash
feat(week-04): add PostgreSQL container exercises
fix(week-02): correct Dockerfile EXPOSE port
docs(week-06): improve Swagger annotations explanation
style(week-03): format code examples
refactor(week-05): simplify JPA relationship examples
test(week-07): add TestContainers integration test
chore(deps): update Spring Boot to 3.2.1
```

---

## ❓ ¿Preguntas?

Si tienes dudas:

1. Revisa la [documentación existente](_docs/)
2. Busca en [issues existentes](../../issues)
3. Abre una [discusión](../../discussions)
4. Crea un issue con la etiqueta `question`

---

¡Gracias por contribuir! 🙌
