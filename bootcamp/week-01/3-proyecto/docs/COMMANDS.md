# 📋 Referencia de Comandos - Semana 01

## Docker Básico

### Información y Ayuda
```bash
docker --version              # Ver versión de Docker
docker info                   # Información del sistema Docker
docker --help                 # Ayuda general
docker <command> --help       # Ayuda de un comando específico
```

### Imágenes
```bash
docker images                 # Listar imágenes locales
docker pull <imagen>:<tag>    # Descargar imagen
docker rmi <imagen>           # Eliminar imagen
docker image prune            # Eliminar imágenes sin usar
```

### Contenedores
```bash
docker ps                     # Contenedores en ejecución
docker ps -a                  # Todos los contenedores
docker run <imagen>           # Crear y ejecutar contenedor
docker start <id>             # Iniciar contenedor
docker stop <id>              # Detener contenedor
docker rm <id>                # Eliminar contenedor
docker container prune        # Eliminar contenedores detenidos
```

### Opciones de docker run
```bash
docker run --rm               # Eliminar al terminar
docker run -it                # Interactivo con terminal
docker run -d                 # Modo detached (background)
docker run -p 8080:80         # Mapear puerto host:contenedor
docker run -v /host:/container # Montar volumen
docker run -w /workdir        # Directorio de trabajo
docker run -e VAR=valor       # Variable de entorno
docker run --name nombre      # Nombre del contenedor
```

### Logs y Debugging
```bash
docker logs <id>              # Ver logs del contenedor
docker logs -f <id>           # Seguir logs en tiempo real
docker exec -it <id> bash     # Ejecutar comando en contenedor
docker inspect <id>           # Información detallada
```

---

## Docker Compose

### Comandos Principales
```bash
docker compose up             # Crear e iniciar servicios
docker compose up -d          # En modo detached
docker compose up <service>   # Solo un servicio específico
docker compose down           # Detener y eliminar servicios
docker compose ps             # Listar servicios
docker compose logs           # Ver logs de todos los servicios
docker compose logs <service> # Logs de un servicio
```

### Ejecución de Comandos
```bash
docker compose run <service> <cmd>    # Ejecutar comando
docker compose run --rm <service>     # Eliminar al terminar
docker compose exec <service> <cmd>   # Ejecutar en servicio corriendo
```

### Construcción
```bash
docker compose build          # Construir imágenes
docker compose build --no-cache # Sin caché
docker compose pull           # Descargar imágenes
```

### Perfiles
```bash
docker compose --profile tools up     # Activar perfil
docker compose --profile tools run --rm compile
```

---

## Comandos del Proyecto

### Compilar y Ejecutar
```bash
# Ejecutar todo (compilar + ejecutar)
docker compose up app

# Solo compilar
docker compose --profile tools run --rm compile

# Solo ejecutar
docker compose --profile tools run --rm run

# Entorno de desarrollo interactivo
docker compose run --rm dev
```

### Limpieza
```bash
# Detener servicios
docker compose down

# Eliminar archivos compilados
rm -rf out

# Limpieza completa
docker compose down -v
docker system prune
```

### Desarrollo
```bash
# Dentro del contenedor dev:
javac src/*.java -d out       # Compilar
java -cp out Main             # Ejecutar
java -cp out Main arg1 arg2   # Con argumentos
```

---

## Java en Docker

### Compilación
```bash
# Compilar un archivo
javac Main.java

# Compilar con directorio de salida
javac Main.java -d out

# Compilar múltiples archivos
javac src/*.java -d out

# Compilar con classpath
javac -cp lib/* src/*.java -d out
```

### Ejecución
```bash
# Ejecutar clase
java Main

# Con classpath
java -cp out Main

# Con argumentos
java -cp out Main arg1 arg2

# Con opciones de JVM
java -Xmx256m -cp out Main
```

### Información de Java
```bash
java --version                # Versión de Java
javac --version               # Versión del compilador
java -XshowSettings:all       # Toda la configuración
```

---

## Variables de Entorno

### En Terminal
```bash
# Definir variable temporal
export VAR=valor

# Ver variable
echo $VAR

# Ejecutar con variable
VAR=valor docker compose up

# Ver todas las variables
env
printenv
```

### En Docker Compose
```yaml
services:
  app:
    environment:
      - VAR=valor             # Directa
    env_file:
      - .env                  # Desde archivo
```

### En Java
```java
// Leer variable de entorno
String value = System.getenv("VAR");

// Con valor por defecto
String value = System.getenv().getOrDefault("VAR", "default");
```
