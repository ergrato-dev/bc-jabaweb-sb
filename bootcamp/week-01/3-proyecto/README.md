# 📦 Proyecto Semana 01: Entorno Docker para Java

## 🎯 Descripción

Este proyecto demuestra la configuración de un entorno de desarrollo Java containerizado usando Docker y Docker Compose. Es el punto de partida para el desarrollo de APIs REST con Spring Boot.

## 📁 Estructura del Proyecto

```
3-proyecto/
├── README.md           # Este archivo
├── Dockerfile          # Construcción de imagen personalizada
├── docker-compose.yml  # Orquestación de servicios
├── .env.example        # Variables de entorno de ejemplo
├── .gitignore          # Archivos a ignorar
├── src/
│   └── Main.java       # Programa de demostración
├── docs/
│   └── COMMANDS.md     # Referencia de comandos
└── out/                # Archivos compilados (ignorado por git)
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Docker Desktop instalado y funcionando
- Terminal (bash, zsh, PowerShell)

### Ejecutar el proyecto

```bash
# 1. Copiar archivo de variables de entorno
cp .env.example .env

# 2. Ejecutar la aplicación
docker compose up app

# 3. (Alternativa) Entorno de desarrollo interactivo
docker compose run --rm dev
```

## 📋 Comandos Disponibles

### Usando Docker Compose

```bash
# Ejecutar aplicación completa (compila + ejecuta)
docker compose up app

# Solo compilar
docker compose --profile tools run --rm compile

# Solo ejecutar (requiere compilar primero)
docker compose --profile tools run --rm run

# Entorno de desarrollo interactivo
docker compose run --rm dev

# Detener todos los servicios
docker compose down

# Ver logs
docker compose logs app

# Reconstruir imagen
docker compose build
```

### Limpieza

```bash
# Eliminar contenedores
docker compose down

# Eliminar archivos compilados
rm -rf out

# Eliminar todo (contenedores + volúmenes)
docker compose down -v
```

## 🔧 Configuración

### Variables de Entorno (.env)

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `APP_NAME` | Nombre de la aplicación | Bootcamp Java Web |
| `APP_VERSION` | Versión | 1.0.0 |
| `APP_ENV` | Entorno (development/production) | development |
| `JAVA_OPTS` | Opciones de JVM | -Xmx256m |

## 📝 Notas

- Este proyecto usa **JDK 21** (Eclipse Temurin)
- Los archivos fuente están en `src/`
- Los archivos compilados se guardan en `out/`
- El directorio `out/` está ignorado por git

## 🎓 Objetivos de Aprendizaje

Al completar este proyecto, deberías ser capaz de:

1. ✅ Entender la estructura de un proyecto Docker para Java
2. ✅ Usar Docker Compose para gestionar servicios
3. ✅ Configurar variables de entorno
4. ✅ Compilar y ejecutar programas Java en contenedores
5. ✅ Mantener un entorno de desarrollo reproducible

---

## 📚 Recursos

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Eclipse Temurin](https://adoptium.net/)
