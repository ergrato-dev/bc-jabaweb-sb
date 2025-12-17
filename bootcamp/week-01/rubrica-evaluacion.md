# 📋 Rúbrica de Evaluación - Semana 01

## Entorno de Desarrollo con Docker y Fundamentos REST

**Duración**: 5 horas
**Peso Total**: 100 puntos

---

## 📊 Distribución de Evidencias

| Tipo de Evidencia | Peso | Descripción |
|-------------------|------|-------------|
| Conocimiento | 30% | Cuestionario teórico |
| Desempeño | 40% | Ejercicios prácticos en clase |
| Producto | 30% | Entregables finales |

---

## 1. Evidencia de Conocimiento (30 puntos)

### Cuestionario sobre Docker y REST

**Instrumento**: Cuestionario de 15 preguntas (2 puntos c/u)

| Tema | Preguntas | Puntos |
|------|-----------|--------|
| Docker conceptos básicos | 5 | 10 |
| Comandos Docker | 4 | 8 |
| Arquitectura cliente-servidor | 2 | 4 |
| Protocolo HTTP | 2 | 4 |
| Principios REST | 2 | 4 |
| **Total** | **15** | **30** |

### Criterios de Evaluación

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 27-30 | Responde correctamente 90%+ de las preguntas |
| Bueno | 21-26 | Responde correctamente 70-89% de las preguntas |
| Suficiente | 18-20 | Responde correctamente 60-69% de las preguntas |
| Insuficiente | <18 | Responde menos del 60% correctamente |

### Preguntas de Ejemplo

1. ¿Cuál es la diferencia entre una imagen y un contenedor Docker?
2. ¿Qué comando se usa para listar contenedores en ejecución?
3. ¿Qué significa que REST sea "stateless"?
4. ¿Qué método HTTP se usa para crear un nuevo recurso?
5. ¿Qué código de estado indica que un recurso no fue encontrado?

---

## 2. Evidencia de Desempeño (40 puntos)

### Ejercicios Prácticos en Clase

**Instrumento**: Lista de cotejo de actividades

| Actividad | Puntos | Criterio |
|-----------|--------|----------|
| Instalación Docker verificada | 5 | Docker funcionando correctamente |
| Ejecutar primer contenedor | 5 | `docker run hello-world` exitoso |
| Descargar imagen Java | 5 | `docker pull eclipse-temurin:21-jdk` |
| Compilar Java en contenedor | 10 | Compilar y ejecutar HelloWorld.java |
| Usar volúmenes | 8 | Montar directorio local correctamente |
| Usar Docker Compose | 7 | Ejecutar docker-compose.yml funcional |
| **Total** | **40** | |

### Criterios de Evaluación por Actividad

#### Compilar Java en contenedor (10 puntos)

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 9-10 | Compila y ejecuta sin ayuda, entiende el proceso |
| Bueno | 7-8 | Compila y ejecuta con mínima ayuda |
| Suficiente | 6 | Compila y ejecuta con guía paso a paso |
| Insuficiente | <6 | No logra compilar o ejecutar |

#### Usar Docker Compose (7 puntos)

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 7 | Crea y ejecuta docker-compose.yml propio |
| Bueno | 5-6 | Modifica docker-compose.yml proporcionado |
| Suficiente | 4 | Ejecuta docker-compose.yml sin modificar |
| Insuficiente | <4 | No logra usar Docker Compose |

---

## 3. Evidencia de Producto (30 puntos)

### Entregables

> ⚠️ **IMPORTANTE**: Los archivos del proyecto son **plantillas con TODOs** que el estudiante debe completar. El objetivo es escribir el código, no copiarlo.

| Entregable | Puntos | Descripción |
|------------|--------|-------------|
| Dockerfile | 10 | Completar todos los TODOs (7 instrucciones) |
| docker-compose.yml | 10 | Completar servicios dev y app |
| Main.java | 10 | Completar los 9 TODOs del programa |
| **Total** | **30** | |

### Rúbrica: Dockerfile (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|--------------|------------------|-------------------|
| Imagen base | FROM correcto | FROM con tag incorrecto | FROM incompleto | Sin FROM |
| Metadatos | LABEL con 3+ campos | LABEL con 2 campos | LABEL básico | Sin LABEL |
| Directorio | WORKDIR correcto | WORKDIR con path diferente | WORKDIR con errores | Sin WORKDIR |
| Directorios | RUN mkdir correcto | Crea directorios parciales | Sintaxis incorrecta | No crea directorios |
| Variables | ENV con 3+ variables | ENV con 2 variables | ENV básico | Sin variables |
| Puerto | EXPOSE 8080 | EXPOSE otro puerto | Sintaxis incorrecta | Sin EXPOSE |
| Comando | CMD correcto | CMD alternativo válido | CMD con errores | Sin CMD |
| Funcionalidad | `docker build` exitoso | Build con warnings | Build con errores menores | No compila |

### Rúbrica: docker-compose.yml (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|--------------|------------------|-------------------|
| Servicio dev | Completo y funcional | Funcional con mejoras | Parcialmente completo | Incompleto |
| Servicio app | Completo con compile+run | Solo compila o solo ejecuta | Parcialmente funcional | No implementado |
| Volúmenes | Correctos y con :ro donde aplica | Volúmenes básicos | Volúmenes con errores | Sin volúmenes |
| Variables | env_file configurado | Variables directas | Parcial | Sin variables |
| Interactivo | stdin_open y tty | Solo uno de los dos | Configuración diferente | Sin configurar |
| Sintaxis | `docker compose config` OK | Warnings menores | Errores menores | No valida |

### Rúbrica: Main.java (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|--------------|------------------|-------------------|
| Banner | Personalizado y decorado | Banner básico | Solo println | Sin banner |
| System Info | 5+ propiedades mostradas | 3-4 propiedades | 1-2 propiedades | Sin info |
| Env Vars | 3+ variables con manejo null | 2 variables | 1 variable | Sin variables |
| Argumentos | Loop con formato [i] | Loop básico | Impresión directa | Sin argumentos |
| Compila | Sin errores ni warnings | Warnings menores | Requiere correcciones | No compila |
| Estructura | Métodos bien organizados | Métodos básicos | Todo en main | Desordenado |
| Desafío extra | Implementa 1+ extras | Intenta extras | No implementa | - |

---

## 📝 Formato de Entrega

### Estructura esperada
```
entrega-semana01/
├── Dockerfile              # Completado por el estudiante
├── docker-compose.yml      # Completado por el estudiante
├── .env                    # Configuración personalizada
└── src/
    └── Main.java           # Completado por el estudiante
```

### Verificación antes de entregar
```bash
# 1. El Dockerfile debe construir correctamente
docker build -t mi-proyecto .

# 2. El docker-compose debe validar sin errores
docker compose config

# 3. El programa debe compilar y ejecutar
docker compose run --rm dev
# Dentro del contenedor:
javac src/Main.java -d out
java -cp out Main
java -cp out Main arg1 arg2
```

### Método de entrega
- Repositorio Git (GitHub/GitLab)
- O carpeta comprimida (.zip)

### Fecha límite
- Al final de la sesión o según indique el instructor

---

## 🎯 Criterios de Aprobación

| Calificación | Puntos | Porcentaje |
|--------------|--------|------------|
| **Aprobado** | ≥60 | ≥60% |
| No aprobado | <60 | <60% |

### Distribución mínima para aprobar
- Conocimiento: ≥18/30 puntos
- Desempeño: ≥24/40 puntos
- Producto: ≥18/30 puntos

---

## 📌 Observaciones

1. **Trabajo colaborativo**: Se permite trabajo en parejas para desempeño
2. **Producto individual**: Los entregables deben ser individuales
3. **Retroalimentación**: Se proporcionará feedback en cada evidencia
4. **Recuperación**: Posibilidad de mejorar producto hasta siguiente sesión

---

## ✅ Checklist del Estudiante

Antes de entregar, verifica:

**Dockerfile:**
- [ ] Tiene instrucción FROM con imagen Java
- [ ] Tiene al menos 2 LABEL (maintainer, description)
- [ ] Tiene WORKDIR /app
- [ ] Crea directorios con RUN mkdir
- [ ] Define variables con ENV
- [ ] Tiene EXPOSE 8080
- [ ] Tiene CMD ["bash"]
- [ ] `docker build -t mi-proyecto .` funciona

**docker-compose.yml:**
- [ ] Servicio `dev` completo y funcional
- [ ] Servicio `app` compila y ejecuta
- [ ] Volúmenes montados correctamente
- [ ] Variables de entorno configuradas
- [ ] `docker compose config` no muestra errores

**Main.java:**
- [ ] Compila sin errores: `javac src/Main.java -d out`
- [ ] Muestra banner personalizado con mi nombre
- [ ] Muestra 5+ propiedades del sistema
- [ ] Muestra 3+ variables de entorno
- [ ] Maneja null en variables de entorno
- [ ] Muestra argumentos si se proporcionan
- [ ] Código organizado en métodos

---

*Semana 01 - Bootcamp Java Web con Spring Boot*
