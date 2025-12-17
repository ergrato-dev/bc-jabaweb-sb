# 📦 Proyecto Semana 01: Entorno Docker para Java

## 🎯 Descripción

Este proyecto es un **ejercicio de integración** donde aplicarás todos los conceptos aprendidos durante la semana. Los archivos están diseñados como **plantillas con TODOs** que debes completar.

> ⚠️ **IMPORTANTE**: No copies y pegues código. El objetivo es que escribas cada línea para reforzar tu aprendizaje.

## 🏆 Objetivos del Proyecto

Al completar este proyecto, habrás demostrado que puedes:

1. ✅ Escribir un **Dockerfile** funcional desde cero
2. ✅ Configurar **Docker Compose** para desarrollo Java
3. ✅ Crear un programa Java que lea **propiedades del sistema**
4. ✅ Usar **variables de entorno** en contenedores
5. ✅ Integrar todos los conceptos de la semana

## 📁 Estructura del Proyecto

```
3-proyecto/
├── README.md           # Este archivo (instrucciones)
├── Dockerfile          # 📝 EJERCICIO: Completa los TODOs
├── docker-compose.yml  # 📝 EJERCICIO: Completa los TODOs
├── .env.example        # Copia a .env y personaliza
├── .gitignore          # Archivos a ignorar
├── src/
│   └── Main.java       # 📝 EJERCICIO: Completa los TODOs
├── docs/
│   └── COMMANDS.md     # Referencia de comandos
└── out/                # Archivos compilados (se genera automáticamente)
```

## 🚀 Instrucciones Paso a Paso

### Paso 1: Preparar el entorno

```bash
# 1. Entra a la carpeta del proyecto
cd bootcamp/week-01/3-proyecto

# 2. Copia el archivo de variables de entorno
cp .env.example .env

# 3. Edita .env con tus datos (opcional)
nano .env   # o usa tu editor favorito
```

### Paso 2: Completa el Dockerfile

Abre `Dockerfile` y completa cada TODO siguiendo las instrucciones en los comentarios.

**Conceptos que aplicarás:**
- FROM, LABEL, WORKDIR, RUN, ENV, EXPOSE, CMD

**Verificación:**
```bash
# Valida que el Dockerfile esté correcto
docker build -t bootcamp-java .

# Si funciona, verás "Successfully built..."
```

### Paso 3: Completa el docker-compose.yml

Abre `docker-compose.yml` y completa cada TODO.

**Conceptos que aplicarás:**
- services, image, container_name, working_dir
- volumes, env_file, stdin_open, tty, command

**Verificación:**
```bash
# Valida la sintaxis
docker compose config

# Si hay errores, revisa la indentación (usa espacios, no tabs)
```

### Paso 4: Completa Main.java

Abre `src/Main.java` y completa los 9 TODOs.

**Conceptos que aplicarás:**
- System.getProperty() para propiedades del sistema
- System.getenv() para variables de entorno
- Manejo de argumentos con args[]
- Bucles for y condicionales if

### Paso 5: Compila y ejecuta

```bash
# Opción A: Usando el servicio de desarrollo
docker compose run --rm dev

# Dentro del contenedor:
javac src/Main.java -d out
java -cp out Main
java -cp out Main arg1 arg2   # con argumentos
exit

# Opción B: Usando el servicio app (si lo completaste)
docker compose up app
```

## ✅ Checklist de Verificación

Antes de entregar, asegúrate de que:

- [ ] `docker build -t bootcamp-java .` funciona sin errores
- [ ] `docker compose config` no muestra errores
- [ ] `docker compose run --rm dev` inicia un contenedor
- [ ] El programa compila: `javac src/Main.java -d out`
- [ ] El programa ejecuta y muestra:
  - [ ] Banner de bienvenida con tu nombre
  - [ ] Información del sistema (Java version, OS, etc.)
  - [ ] Variables de entorno (APP_NAME, APP_VERSION, APP_ENV)
  - [ ] Argumentos (si se proporcionan)

## 🔧 Troubleshooting

### "No source image provided with `FROM`"
- Revisa el TODO 1 en el Dockerfile
- Asegúrate de escribir: `FROM eclipse-temurin:21-jdk`

### "Incorrect type. Expected string"
- El YAML requiere valores después de los dos puntos
- Ejemplo: `image: eclipse-temurin:21-jdk` (no dejes el valor vacío)

### "javac: file not found: src/Main.java"
- Verifica que estés en el directorio correcto (/app)
- Verifica que los volúmenes estén bien configurados

### El programa compila pero no muestra variables de entorno
- Verifica que hayas copiado `.env.example` a `.env`
- Verifica que `env_file: - .env` esté en docker-compose.yml

## 📚 Recursos de Ayuda

- [docs/COMMANDS.md](docs/COMMANDS.md) - Referencia de comandos
- [Dockerfile Reference](https://docs.docker.com/engine/reference/dockerfile/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)

## 🎓 Criterios de Evaluación

| Criterio | Puntos |
|----------|--------|
| Dockerfile completo y funcional | 4 |
| docker-compose.yml con 2 servicios | 4 |
| Main.java con todas las funciones | 4 |
| Programa ejecuta correctamente | 3 |
| Código limpio y comentado | 2 |
| **Total** | **17** |

---

> 💡 **Consejo**: Si te atascas, revisa el material de teoría y las prácticas. Todos los conceptos necesarios están explicados ahí.
