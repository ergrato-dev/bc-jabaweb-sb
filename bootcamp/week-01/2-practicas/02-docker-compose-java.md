# Práctica 02: Docker Compose para Desarrollo Java

## 🎯 Objetivos

- Entender qué es Docker Compose y para qué sirve
- Crear un archivo `docker-compose.yml` básico
- Configurar un entorno de desarrollo Java reproducible
- Usar perfiles para diferentes configuraciones

---

## 1. ¿Qué es Docker Compose?

Docker Compose es una herramienta para definir y ejecutar aplicaciones Docker multi-contenedor.

**Sin Docker Compose** (muchos comandos largos):
```bash
docker run --rm -v $(pwd):/app -w /app -p 8080:8080 --name mi-app eclipse-temurin:21-jdk java -jar app.jar
docker run -d --name db -e POSTGRES_PASSWORD=secret postgres:16
docker network create mi-red
# ... y más comandos
```

**Con Docker Compose** (un solo archivo):
```yaml
# docker-compose.yml
services:
  app:
    image: eclipse-temurin:21-jdk
    volumes:
      - .:/app
    ports:
      - "8080:8080"
  db:
    image: postgres:16
    environment:
      POSTGRES_PASSWORD: secret
```

```bash
# Un solo comando para todo
docker compose up
```

---

## 2. Estructura del Archivo docker-compose.yml

### 2.1 Sintaxis Básica

```yaml
# version: ya no es necesario en versiones recientes

services:
  nombre-servicio:
    image: imagen:tag
    # ... configuración del servicio

  otro-servicio:
    image: otra-imagen
    # ...

volumes:
  nombre-volumen:

networks:
  nombre-red:
```

### 2.2 Opciones Comunes

```yaml
services:
  mi-servicio:
    image: imagen:tag           # Imagen a usar
    build: ./path               # O construir desde Dockerfile
    container_name: mi-nombre   # Nombre del contenedor
    ports:
      - "8080:8080"             # Puerto host:contenedor
    volumes:
      - ./src:/app/src          # Bind mount
      - datos:/app/data         # Named volume
    environment:
      - VAR=valor               # Variables de entorno
    env_file:
      - .env                    # Archivo con variables
    working_dir: /app           # Directorio de trabajo
    command: java -jar app.jar  # Comando a ejecutar
    depends_on:
      - otro-servicio           # Dependencias
    networks:
      - mi-red                  # Redes
    restart: unless-stopped     # Política de reinicio
```

---

## Ejercicio 1: Entorno Java Básico

### 1.1 Crear estructura del proyecto

```bash
# Crear directorio
mkdir -p ~/bootcamp-java/week01-compose
cd ~/bootcamp-java/week01-compose

# Crear estructura
mkdir -p src
```

### 1.2 Crear archivo Java

```bash
cat > src/Main.java << 'EOF'
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   Bootcamp Java Web - Docker Compose  ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Java: " + System.getProperty("java.version") + "                         ║");
        System.out.println("║  OS: " + System.getProperty("os.name") + "                       ║");
        System.out.println("║  User: " + System.getProperty("user.name") + "                          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        // Mostrar variables de entorno
        String appEnv = System.getenv("APP_ENV");
        String appName = System.getenv("APP_NAME");
        
        if (appEnv != null) {
            System.out.println("\n📦 Environment: " + appEnv);
        }
        if (appName != null) {
            System.out.println("📝 App Name: " + appName);
        }
    }
}
EOF
```

### 1.3 Crear docker-compose.yml

```bash
cat > docker-compose.yml << 'EOF'
services:
  java:
    image: eclipse-temurin:21-jdk
    container_name: bootcamp-java
    working_dir: /app
    volumes:
      - ./src:/app/src
    environment:
      - APP_ENV=development
      - APP_NAME=Bootcamp Java Web
    command: >
      sh -c "
        echo '🔨 Compilando...' &&
        javac src/Main.java -d out &&
        echo '🚀 Ejecutando...' &&
        java -cp out Main
      "
EOF
```

### 1.4 Ejecutar

```bash
# Ejecutar
docker compose up

# En otra terminal, ver contenedor
docker compose ps

# Detener
docker compose down
```

---

## Ejercicio 2: Modo Desarrollo Interactivo

### 2.1 Agregar servicio de desarrollo

```bash
cat > docker-compose.yml << 'EOF'
services:
  # Servicio para ejecutar comandos
  java:
    image: eclipse-temurin:21-jdk
    container_name: bootcamp-java
    working_dir: /app
    volumes:
      - ./src:/app/src
      - ./out:/app/out
    environment:
      - APP_ENV=development
      - APP_NAME=Bootcamp Java Web

  # Servicio de desarrollo interactivo
  dev:
    image: eclipse-temurin:21-jdk
    container_name: bootcamp-java-dev
    working_dir: /app
    volumes:
      - ./src:/app/src
      - ./out:/app/out
    environment:
      - APP_ENV=development
    stdin_open: true    # Equivalente a -i
    tty: true           # Equivalente a -t
    command: bash
EOF
```

### 2.2 Usar el entorno de desarrollo

```bash
# Iniciar entorno de desarrollo interactivo
docker compose run --rm dev

# Dentro del contenedor:
javac src/Main.java -d out
java -cp out Main
exit
```

### 2.3 Ejecutar comandos directos

```bash
# Compilar
docker compose run --rm java javac src/Main.java -d out

# Ejecutar
docker compose run --rm java java -cp out Main

# Ver versión de Java
docker compose run --rm java java --version
```

---

## Ejercicio 3: Variables de Entorno con .env

### 3.1 Crear archivo .env

```bash
cat > .env << 'EOF'
# Configuración del proyecto
APP_NAME=Bootcamp Java Web
APP_ENV=development
APP_VERSION=1.0.0

# Configuración de Java
JAVA_OPTS=-Xmx256m -Xms128m
EOF
```

### 3.2 Actualizar docker-compose.yml

```bash
cat > docker-compose.yml << 'EOF'
services:
  java:
    image: eclipse-temurin:21-jdk
    container_name: bootcamp-java
    working_dir: /app
    volumes:
      - ./src:/app/src
      - ./out:/app/out
    env_file:
      - .env
    command: >
      sh -c "
        echo '📋 Configuración:' &&
        echo '   APP_NAME: ${APP_NAME}' &&
        echo '   APP_ENV: ${APP_ENV}' &&
        echo '   APP_VERSION: ${APP_VERSION}' &&
        echo '' &&
        javac src/Main.java -d out &&
        java ${JAVA_OPTS} -cp out Main
      "

  dev:
    image: eclipse-temurin:21-jdk
    container_name: bootcamp-java-dev
    working_dir: /app
    volumes:
      - ./src:/app/src
      - ./out:/app/out
    env_file:
      - .env
    stdin_open: true
    tty: true
    command: bash
EOF
```

### 3.3 Ejecutar con variables

```bash
docker compose up java
```

### 3.4 Sobrescribir variables

```bash
# Sobrescribir variable desde línea de comandos
APP_ENV=production docker compose up java
```

---

## Ejercicio 4: Múltiples Archivos Java

### 4.1 Crear más clases

```bash
# Clase de utilidades
cat > src/Utils.java << 'EOF'
public class Utils {
    public static void printSeparator() {
        System.out.println("─".repeat(40));
    }
    
    public static void printHeader(String title) {
        printSeparator();
        System.out.println("  " + title);
        printSeparator();
    }
    
    public static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}
EOF

# Actualizar Main
cat > src/Main.java << 'EOF'
public class Main {
    public static void main(String[] args) {
        Utils.printHeader("Bootcamp Java Web con Docker");
        
        System.out.println("  Java: " + System.getProperty("java.version"));
        System.out.println("  OS: " + System.getProperty("os.name"));
        System.out.println("  Entorno: " + Utils.getEnvOrDefault("APP_ENV", "unknown"));
        System.out.println("  Versión: " + Utils.getEnvOrDefault("APP_VERSION", "0.0.0"));
        
        Utils.printSeparator();
        
        if (args.length > 0) {
            System.out.println("\n📝 Argumentos recibidos:");
            for (int i = 0; i < args.length; i++) {
                System.out.println("  [" + i + "] " + args[i]);
            }
        }
    }
}
EOF
```

### 4.2 Compilar y ejecutar

```bash
# Compilar todas las clases
docker compose run --rm java sh -c "javac src/*.java -d out"

# Ejecutar
docker compose run --rm java java -cp out Main

# Ejecutar con argumentos
docker compose run --rm java java -cp out Main arg1 arg2 "argumento con espacios"
```

---

## Ejercicio 5: Script de Desarrollo

### 5.1 Crear scripts útiles

```bash
# Script para compilar
cat > compile.sh << 'EOF'
#!/bin/bash
echo "🔨 Compilando proyecto..."
docker compose run --rm java sh -c "javac src/*.java -d out"
echo "✅ Compilación completada"
EOF

# Script para ejecutar
cat > run.sh << 'EOF'
#!/bin/bash
echo "🚀 Ejecutando aplicación..."
docker compose run --rm java java -cp out Main "$@"
EOF

# Script para desarrollo
cat > dev.sh << 'EOF'
#!/bin/bash
echo "🔧 Iniciando entorno de desarrollo..."
docker compose run --rm dev
EOF

# Hacer ejecutables
chmod +x compile.sh run.sh dev.sh
```

### 5.2 Usar los scripts

```bash
# Compilar
./compile.sh

# Ejecutar
./run.sh

# Ejecutar con argumentos
./run.sh hola mundo

# Entrar al entorno de desarrollo
./dev.sh
```

---

## Ejercicio 6: Proyecto Final de la Semana

### 6.1 Estructura completa

```bash
# Crear estructura del proyecto
mkdir -p ~/bootcamp-java/week01-project
cd ~/bootcamp-java/week01-project
mkdir -p src docs
```

### 6.2 Crear docker-compose.yml final

```bash
cat > docker-compose.yml << 'EOF'
# Docker Compose - Bootcamp Java Web - Semana 01
# Entorno de desarrollo reproducible con JDK 21

services:
  # Servicio principal para compilar y ejecutar
  app:
    image: eclipse-temurin:21-jdk-alpine
    container_name: week01-app
    working_dir: /app
    volumes:
      - ./src:/app/src:ro       # Código fuente (solo lectura)
      - ./out:/app/out          # Archivos compilados
      - ./docs:/app/docs        # Documentación
    env_file:
      - .env
    command: >
      sh -c "
        echo '════════════════════════════════════════' &&
        echo '  Bootcamp Java Web - Proyecto Semana 1' &&
        echo '════════════════════════════════════════' &&
        echo '' &&
        echo '🔨 Compilando...' &&
        javac src/*.java -d out 2>&1 &&
        echo '✅ Compilación exitosa' &&
        echo '' &&
        echo '🚀 Ejecutando...' &&
        echo '' &&
        java -cp out Main
      "

  # Entorno de desarrollo interactivo
  dev:
    image: eclipse-temurin:21-jdk
    container_name: week01-dev
    working_dir: /app
    volumes:
      - ./src:/app/src
      - ./out:/app/out
      - ./docs:/app/docs
    env_file:
      - .env
    stdin_open: true
    tty: true
    command: bash

  # Solo compilar
  compile:
    image: eclipse-temurin:21-jdk-alpine
    working_dir: /app
    volumes:
      - ./src:/app/src:ro
      - ./out:/app/out
    command: sh -c "javac src/*.java -d out && echo '✅ Compilado'"
    profiles:
      - tools

  # Solo ejecutar (requiere compilar primero)
  run:
    image: eclipse-temurin:21-jdk-alpine
    working_dir: /app
    volumes:
      - ./out:/app/out:ro
    env_file:
      - .env
    command: java -cp out Main
    profiles:
      - tools
EOF
```

### 6.3 Crear .env

```bash
cat > .env << 'EOF'
# Bootcamp Java Web - Semana 01
# Configuración del entorno

APP_NAME=Bootcamp Java Web
APP_VERSION=1.0.0
APP_ENV=development
JAVA_OPTS=-Xmx256m
EOF
```

### 6.4 Crear .gitignore

```bash
cat > .gitignore << 'EOF'
# Archivos compilados
out/
*.class

# IDE
.idea/
*.iml
.vscode/

# Sistema
.DS_Store
Thumbs.db

# Logs
*.log

# Environment local (no commitear)
.env.local
EOF
```

### 6.5 Crear documentación

```bash
cat > docs/REST-API-PRINCIPLES.md << 'EOF'
# Principios REST - Resumen

## Los 6 Principios

1. **Cliente-Servidor**: Separación de responsabilidades
2. **Sin Estado**: Cada request es auto-contenida
3. **Cacheable**: Respuestas pueden cachearse
4. **Interfaz Uniforme**: URIs consistentes, métodos HTTP correctos
5. **Sistema en Capas**: Proxies, load balancers transparentes
6. **Código bajo demanda**: (Opcional) Enviar código ejecutable

## Métodos HTTP → CRUD

| Método | Operación | Ejemplo |
|--------|-----------|---------|
| GET | Read | GET /users |
| POST | Create | POST /users |
| PUT | Update (full) | PUT /users/123 |
| PATCH | Update (partial) | PATCH /users/123 |
| DELETE | Delete | DELETE /users/123 |

## Códigos de Estado

- **2xx**: Éxito (200 OK, 201 Created, 204 No Content)
- **4xx**: Error cliente (400, 401, 403, 404, 422)
- **5xx**: Error servidor (500, 502, 503)
EOF
```

### 6.6 Crear programa final

```bash
cat > src/Main.java << 'EOF'
/**
 * Bootcamp Java Web con Spring Boot
 * Semana 01 - Proyecto Final
 * 
 * Demuestra el uso de Docker y Docker Compose
 * para entornos de desarrollo Java reproducibles.
 */
public class Main {
    public static void main(String[] args) {
        printBanner();
        printSystemInfo();
        printEnvironmentInfo();
        
        if (args.length > 0) {
            printArguments(args);
        }
    }
    
    private static void printBanner() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     BOOTCAMP JAVA WEB - SPRING BOOT          ║");
        System.out.println("║     Semana 01: Docker + Fundamentos REST     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }
    
    private static void printSystemInfo() {
        System.out.println("📋 Información del Sistema:");
        System.out.println("   Java Version: " + System.getProperty("java.version"));
        System.out.println("   Java Vendor:  " + System.getProperty("java.vendor"));
        System.out.println("   OS Name:      " + System.getProperty("os.name"));
        System.out.println("   OS Arch:      " + System.getProperty("os.arch"));
        System.out.println();
    }
    
    private static void printEnvironmentInfo() {
        System.out.println("🔧 Variables de Entorno:");
        printEnvVar("APP_NAME");
        printEnvVar("APP_VERSION");
        printEnvVar("APP_ENV");
        System.out.println();
    }
    
    private static void printEnvVar(String name) {
        String value = System.getenv(name);
        System.out.println("   " + name + ": " + (value != null ? value : "(no definida)"));
    }
    
    private static void printArguments(String[] args) {
        System.out.println("📝 Argumentos recibidos:");
        for (int i = 0; i < args.length; i++) {
            System.out.println("   [" + i + "] " + args[i]);
        }
        System.out.println();
    }
}
EOF
```

### 6.7 Comandos finales

```bash
# Ejecutar aplicación completa
docker compose up app

# Solo compilar
docker compose --profile tools run --rm compile

# Solo ejecutar (después de compilar)
docker compose --profile tools run --rm run

# Entorno de desarrollo
docker compose run --rm dev

# Limpiar todo
docker compose down
rm -rf out
```

---

## ✅ Checklist de la Práctica

- [ ] Entiendo la estructura de docker-compose.yml
- [ ] Puedo crear servicios con imagen, volúmenes y variables
- [ ] Sé usar archivos .env para configuración
- [ ] Puedo ejecutar múltiples servicios con Compose
- [ ] Entiendo la diferencia entre `docker compose up` y `docker compose run`
- [ ] Tengo un entorno de desarrollo Java reproducible

---

## 📦 Entregables de la Semana 01

1. **docker-compose.yml** funcional con JDK 21
2. **Documento REST** (docs/REST-API-PRINCIPLES.md)
3. **Programa Java** que muestre información del sistema
4. **Archivo .env** con configuración del proyecto
