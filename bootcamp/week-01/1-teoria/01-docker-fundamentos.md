# Docker: Solución a Entornos Inestables

## 🎯 Objetivos de Aprendizaje

- Comprender por qué Docker resuelve el problema "en mi máquina funciona"
- Instalar Docker Desktop correctamente
- Entender los conceptos: imágenes, contenedores, volúmenes
- Ejecutar tu primer contenedor

---

## 1. El Problema: "En Mi Máquina Funciona"

### 1.1 Escenario Real en Entornos de Formación

Imagina este escenario común en un bootcamp:

```
Día 1 - Cohorte A:
✅ Instala JDK 21
✅ Configura JAVA_HOME
✅ Todo funciona perfectamente

Día 2 - Cohorte B (mismo equipo):
❌ JDK 21 no existe (alguien lo desinstaló)
❌ JAVA_HOME apunta a JDK 8
❌ Variables de entorno corruptas
❌ 2 horas perdidas en configuración
```

### 1.2 Causas del Problema

| Causa | Descripción |
|-------|-------------|
| **Múltiples usuarios** | Equipos compartidos entre cohortes |
| **Versiones diferentes** | Cada proyecto requiere distintas versiones |
| **Configuraciones globales** | Variables de entorno que se sobreescriben |
| **Permisos** | Restricciones que impiden instalaciones |
| **"Limpieza"** | Administradores que eliminan software |

### 1.3 La Solución: Contenedores

> **Docker** = Entorno completo, aislado y reproducible en cualquier máquina

```
┌─────────────────────────────────────────────────────┐
│                    TU MÁQUINA                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐ │
│  │ Contenedor  │  │ Contenedor  │  │ Contenedor  │ │
│  │   Java 21   │  │   Java 17   │  │   Java 8    │ │
│  │   Maven     │  │   Gradle    │  │   Ant       │ │
│  │   Spring    │  │   Quarkus   │  │   Legacy    │ │
│  └─────────────┘  └─────────────┘  └─────────────┘ │
│                                                     │
│              Docker Engine                          │
└─────────────────────────────────────────────────────┘
```

---

## 2. Instalación de Docker Desktop

### 2.1 Requisitos del Sistema

#### Windows
- Windows 10/11 64-bit (Home, Pro, Enterprise, Education)
- WSL 2 habilitado
- BIOS: Virtualización habilitada (VT-x/AMD-V)
- 4GB RAM mínimo (8GB recomendado)

#### macOS
- macOS 12 (Monterey) o superior
- Chip Intel o Apple Silicon (M1/M2/M3)
- 4GB RAM mínimo

#### Linux
- Ubuntu 20.04+, Debian 11+, Fedora 36+
- Kernel 4.0+
- KVM virtualización habilitada

### 2.2 Pasos de Instalación

#### Windows

```powershell
# 1. Habilitar WSL 2
wsl --install

# 2. Descargar Docker Desktop
# https://www.docker.com/products/docker-desktop/

# 3. Instalar y reiniciar

# 4. Verificar instalación
docker --version
docker run hello-world
```

#### macOS

```bash
# Opción 1: Descarga directa
# https://www.docker.com/products/docker-desktop/

# Opción 2: Homebrew
brew install --cask docker

# Verificar instalación
docker --version
docker run hello-world
```

#### Linux (Ubuntu/Debian)

```bash
# 1. Actualizar repositorios
sudo apt update

# 2. Instalar dependencias
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common

# 3. Agregar clave GPG de Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# 4. Agregar repositorio
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# 5. Instalar Docker
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# 6. Agregar usuario al grupo docker (evita usar sudo)
sudo usermod -aG docker $USER
newgrp docker

# 7. Verificar
docker --version
docker run hello-world
```

### 2.3 Verificación de Instalación

```bash
# Versión de Docker
docker --version
# Docker version 24.0.7, build afdd53b

# Versión de Docker Compose
docker compose version
# Docker Compose version v2.23.3

# Información del sistema
docker info

# Test de funcionamiento
docker run hello-world
```

---

## 3. Conceptos Fundamentales

### 3.1 Imágenes (Images)

Una **imagen** es una plantilla de solo lectura con instrucciones para crear un contenedor.

```
┌────────────────────────────────────┐
│           IMAGEN                   │
│  ┌──────────────────────────────┐  │
│  │ Capa 4: Tu aplicación        │  │
│  ├──────────────────────────────┤  │
│  │ Capa 3: Dependencias (Maven) │  │
│  ├──────────────────────────────┤  │
│  │ Capa 2: JDK 21               │  │
│  ├──────────────────────────────┤  │
│  │ Capa 1: Sistema Operativo    │  │
│  │         (Alpine Linux)       │  │
│  └──────────────────────────────┘  │
└────────────────────────────────────┘
```

**Características**:
- Inmutables (no cambian)
- Se construyen en capas
- Se almacenan en registros (Docker Hub, GitHub Container Registry)
- Se identifican por nombre y tag: `eclipse-temurin:21-jdk`

```bash
# Ver imágenes locales
docker images

# Descargar una imagen
docker pull eclipse-temurin:21-jdk

# Buscar imágenes
docker search openjdk
```

### 3.2 Contenedores (Containers)

Un **contenedor** es una instancia ejecutable de una imagen.

```
┌─────────────────────────────────────────────┐
│              CONTENEDOR                      │
│  ┌───────────────────────────────────────┐  │
│  │         Capa de escritura             │  │ ← Efímera
│  │    (cambios durante ejecución)        │  │
│  └───────────────────────────────────────┘  │
│  ┌───────────────────────────────────────┐  │
│  │              IMAGEN                   │  │ ← Solo lectura
│  │         (capas inmutables)            │  │
│  └───────────────────────────────────────┘  │
└─────────────────────────────────────────────┘
```

**Características**:
- Procesos aislados
- Tienen su propio sistema de archivos
- Pueden crearse, iniciarse, detenerse, eliminarse
- Múltiples contenedores de la misma imagen

```bash
# Crear y ejecutar contenedor
docker run eclipse-temurin:21-jdk java --version

# Ejecutar en modo interactivo
docker run -it eclipse-temurin:21-jdk bash

# Ver contenedores en ejecución
docker ps

# Ver todos los contenedores
docker ps -a

# Detener contenedor
docker stop <container_id>

# Eliminar contenedor
docker rm <container_id>
```

### 3.3 Volúmenes (Volumes)

Los **volúmenes** permiten persistir datos fuera del contenedor.

```
┌─────────────────────────────────────────────────┐
│                 MÁQUINA HOST                     │
│                                                  │
│   /home/user/proyecto/                          │
│   └── src/                                       │
│       └── Main.java                              │
│              │                                   │
│              │ VOLUMEN (mapeo)                   │
│              ▼                                   │
│   ┌─────────────────────────────────────┐       │
│   │         CONTENEDOR                   │       │
│   │   /app/src/                          │       │
│   │   └── Main.java                      │       │
│   └─────────────────────────────────────┘       │
└─────────────────────────────────────────────────┘
```

**Tipos de volúmenes**:

| Tipo | Uso | Ejemplo |
|------|-----|---------|
| **Bind mount** | Desarrollo (sincronizar código) | `-v ./src:/app/src` |
| **Named volume** | Persistir datos (DB) | `-v postgres_data:/var/lib/postgresql` |
| **tmpfs** | Datos temporales en memoria | `--tmpfs /tmp` |

```bash
# Bind mount - sincronizar carpeta local
docker run -v $(pwd):/app eclipse-temurin:21-jdk ls /app

# Named volume - crear y usar
docker volume create mi_volumen
docker run -v mi_volumen:/data alpine touch /data/archivo.txt

# Listar volúmenes
docker volume ls
```

### 3.4 Redes (Networks)

Las **redes** permiten comunicación entre contenedores.

```
┌─────────────────────────────────────────────────────┐
│                 RED: mi-red                          │
│                                                      │
│   ┌─────────────┐         ┌─────────────┐          │
│   │    app      │  ───►   │     db      │          │
│   │  (Spring)   │         │ (PostgreSQL)│          │
│   │             │         │             │          │
│   │ app:8080    │         │ db:5432     │          │
│   └─────────────┘         └─────────────┘          │
│                                                      │
└─────────────────────────────────────────────────────┘
```

```bash
# Crear red
docker network create mi-red

# Ejecutar contenedor en red
docker run --network mi-red --name app eclipse-temurin:21-jdk

# Los contenedores se encuentran por nombre (DNS interno)
# Desde "app" puedes conectar a "db:5432"
```

---

## 4. Comandos Esenciales

### 4.1 Comandos de Imágenes

```bash
# Listar imágenes
docker images

# Descargar imagen
docker pull <imagen>:<tag>

# Eliminar imagen
docker rmi <imagen>

# Construir imagen desde Dockerfile
docker build -t mi-imagen:1.0 .

# Ver historial de capas
docker history <imagen>
```

### 4.2 Comandos de Contenedores

```bash
# Ejecutar contenedor
docker run [opciones] <imagen> [comando]

# Opciones comunes:
#   -d          Modo detached (background)
#   -it         Modo interactivo con terminal
#   -p 8080:80  Mapear puerto host:contenedor
#   -v ./:/app  Montar volumen
#   --name mi-c Asignar nombre
#   --rm        Eliminar al terminar

# Ejemplos:
docker run -d -p 8080:8080 --name mi-app mi-imagen
docker run -it --rm eclipse-temurin:21-jdk bash
docker run -v $(pwd):/app -w /app eclipse-temurin:21-jdk javac Main.java

# Gestión de contenedores
docker ps                    # En ejecución
docker ps -a                 # Todos
docker stop <id|nombre>      # Detener
docker start <id|nombre>     # Iniciar
docker restart <id|nombre>   # Reiniciar
docker rm <id|nombre>        # Eliminar
docker logs <id|nombre>      # Ver logs
docker exec -it <id> bash    # Entrar a contenedor en ejecución
```

### 4.3 Comandos de Limpieza

```bash
# Eliminar contenedores detenidos
docker container prune

# Eliminar imágenes sin usar
docker image prune

# Eliminar todo lo no usado
docker system prune -a

# Ver uso de disco
docker system df
```

---

## 5. Tu Primer Contenedor Java

### 5.1 Hello World en Docker

```bash
# 1. Ejecutar Java en contenedor
docker run eclipse-temurin:21-jdk java --version

# 2. Crear archivo Java
echo 'public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello from Docker!");
        System.out.println("Java version: " + System.getProperty("java.version"));
    }
}' > HelloWorld.java

# 3. Compilar y ejecutar en contenedor
docker run --rm -v $(pwd):/app -w /app eclipse-temurin:21-jdk sh -c "javac HelloWorld.java && java HelloWorld"
```

### 5.2 Entendiendo el Comando

```bash
docker run --rm -v $(pwd):/app -w /app eclipse-temurin:21-jdk sh -c "javac HelloWorld.java && java HelloWorld"
│       │       │              │       │                       │
│       │       │              │       │                       └── Comando a ejecutar
│       │       │              │       └── Imagen con JDK 21
│       │       │              └── Directorio de trabajo: /app
│       │       └── Montar carpeta actual en /app
│       └── Eliminar contenedor al terminar
└── Comando base
```

---

## 6. Troubleshooting

### 6.1 Errores Comunes

| Error | Causa | Solución |
|-------|-------|----------|
| `permission denied` | Usuario no en grupo docker | `sudo usermod -aG docker $USER` |
| `Cannot connect to Docker daemon` | Docker no está corriendo | Iniciar Docker Desktop |
| `port already in use` | Puerto ocupado | Usar otro puerto `-p 8081:8080` |
| `no space left on device` | Disco lleno | `docker system prune -a` |

### 6.2 Verificar que Todo Funciona

```bash
# Script de verificación
docker run --rm eclipse-temurin:21-jdk java -version && \
echo "✅ Docker funciona correctamente" || \
echo "❌ Hay un problema con Docker"
```

---

## 📚 Recursos Adicionales

- [Documentación oficial de Docker](https://docs.docker.com/)
- [Docker Hub - Imágenes oficiales](https://hub.docker.com/)
- [Eclipse Temurin - JDK en Docker](https://hub.docker.com/_/eclipse-temurin)

---

## ✅ Checklist de la Sección

- [ ] Instalé Docker Desktop
- [ ] Ejecuté `docker run hello-world` exitosamente
- [ ] Entiendo la diferencia entre imagen y contenedor
- [ ] Puedo ejecutar Java en un contenedor
- [ ] Sé usar volúmenes para montar código local
